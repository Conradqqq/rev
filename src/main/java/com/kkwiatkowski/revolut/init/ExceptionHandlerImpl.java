package com.kkwiatkowski.revolut.init;

import com.google.gson.Gson;
import com.kkwiatkowski.revolut.model.dto.response.ErrorDto;
import com.kkwiatkowski.revolut.model.exception.AccountNotFoundException;
import com.kkwiatkowski.revolut.model.exception.InsufficientFundsException;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionHandlerImpl implements ExceptionHandler {

    private final Gson gson;

    @Inject
    public ExceptionHandlerImpl(Gson gson) {
        this.gson = gson;
    }

    public void handleException(Exception exception, Request request, Response response) {
        int status;

        if (exception instanceof AccountNotFoundException) {
            status = 404;
        } else if (exception instanceof InsufficientFundsException) {
            status = 403;
        } else {
            status = 500;
        }

        response.status(status);
        ErrorDto errorDto = ErrorDto.builder()
                .message(exception.getMessage())
                .details(status == 500 ? getStackTrace(exception) : null)
                .build();
        response.body(gson.toJson(errorDto));
    }

    private String getStackTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
