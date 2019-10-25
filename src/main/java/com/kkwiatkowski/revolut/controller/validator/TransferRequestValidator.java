package com.kkwiatkowski.revolut.controller.validator;

import com.kkwiatkowski.revolut.model.dto.request.TransferDto;
import com.kkwiatkowski.revolut.model.exception.RequestValidationException;

import java.math.BigDecimal;

public class TransferRequestValidator implements RequestValidator<TransferDto> {

    @Override
    public void validate(TransferDto request) throws RequestValidationException {
        if (request.getFrom() == request.getTo()) {
            throw new RequestValidationException("Source and destination accounts have to be different");
        }

        if (BigDecimal.ZERO.compareTo(request.getAmount()) >= 0) {
            throw new RequestValidationException("Amount have to be greater than 0");
        }
    }

}
