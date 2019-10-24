package com.kkwiatkowski.revolut.controller.validator;

import com.kkwiatkowski.revolut.model.exception.RequestValidationException;

public interface RequestValidator<Input> {

    void validate(Input request) throws RequestValidationException;

}
