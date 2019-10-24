package com.kkwiatkowski.revolut.controller;

import com.google.gson.Gson;
import com.kkwiatkowski.revolut.controller.validator.RequestValidator;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.dto.response.AccountDto;
import com.kkwiatkowski.revolut.service.AccountService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

public final class AccountControllerImpl implements AccountController {

    private final RequestValidator<String> requestValidator;
    private final AccountService accountService;
    private final Gson gson;

    @Inject
    public AccountControllerImpl(RequestValidator<String> requestValidator, AccountService accountService, Gson gson) {
        this.requestValidator = requestValidator;
        this.accountService = accountService;
        this.gson = gson;
    }

    public String getAccount(Request request, Response response) {
        String accountIdParam = request.params(":id");
        requestValidator.validate(accountIdParam);

        long accountid = Long.parseLong(accountIdParam);
        Account account = accountService.getAccount(accountid);
        AccountDto accountDto = AccountDto.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .build();
        return gson.toJson(accountDto);
    }
}
