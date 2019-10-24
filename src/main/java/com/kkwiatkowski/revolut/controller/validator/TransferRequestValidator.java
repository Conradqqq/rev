package com.kkwiatkowski.revolut.controller.validator;

import com.kkwiatkowski.revolut.model.Transfer;
import com.kkwiatkowski.revolut.model.exception.RequestValidationException;

import java.math.BigDecimal;

public class TransferRequestValidator implements RequestValidator<Transfer> {
    @Override
    public void validate(Transfer request) throws RequestValidationException {
        if (request.getFrom().equals(request.getTo())) {
            throw new RequestValidationException("Source and destination accounts are the same");
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            throw new RequestValidationException("Amount have to be greater than 0");
        }
    }
}
