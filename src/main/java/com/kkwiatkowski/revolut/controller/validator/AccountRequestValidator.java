package com.kkwiatkowski.revolut.controller.validator;

import com.kkwiatkowski.revolut.model.exception.RequestValidationException;

public class AccountRequestValidator implements RequestValidator<String> {

    @Override
    public void validate(String accountId) throws RequestValidationException {
        long parsedAccountId;
        try {
            parsedAccountId = Long.parseLong(accountId);
        } catch (NumberFormatException exception) {
            throw new RequestValidationException("Cannot parse account id. Make sure it's number");
        }

        if (parsedAccountId <= 0) {
            throw new RequestValidationException("Account id must be above 0");
        }
    }

}
