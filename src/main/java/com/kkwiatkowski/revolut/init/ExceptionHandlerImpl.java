package com.kkwiatkowski.revolut.init;

import com.google.gson.Gson;
import com.kkwiatkowski.revolut.model.dto.response.ErrorDto;
import com.kkwiatkowski.revolut.model.exception.AccountNotFoundException;
import com.kkwiatkowski.revolut.model.exception.InsufficientFundsException;
import com.kkwiatkowski.revolut.model.exception.RequestValidationException;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandlerImpl implements ExceptionHandler {

    private static final int BAD_REQUEST_STATUS = 400;
    private static final int FORBIDDEN_STATUS = 403;
    private static final int NOT_FOUND_STATUS = 404;
    private static final int INTERNAL_ERROR_STATUS = 500;
    private final Gson gson;

    @Inject
    public ExceptionHandlerImpl(Gson gson) {
        this.gson = gson;
    }

    public void handleException(Exception exception, Request request, Response response) {
        int status = getStatus(exception);
        ErrorDto errorDto = getErrorDto(exception, status);
        response.body(gson.toJson(errorDto));
        response.status(status);
    }

    private int getStatus(Exception exception) {
        int status;
        if (exception instanceof AccountNotFoundException) {
            status = NOT_FOUND_STATUS;
        } else if (exception instanceof InsufficientFundsException) {
            status = FORBIDDEN_STATUS;
        } else if (exception instanceof RequestValidationException) {
            status = BAD_REQUEST_STATUS;
        } else {
            status = INTERNAL_ERROR_STATUS;
        }
        return status;
    }

    private ErrorDto getErrorDto(Exception exception, int status) {
        ErrorDto.ErrorDtoBuilder errorDto = ErrorDto.builder()
                .message(exception.getMessage());

        if (status == INTERNAL_ERROR_STATUS) {
            errorDto.details(getStackTrace(exception));
        }

        return errorDto.build();
    }

    private String getStackTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
