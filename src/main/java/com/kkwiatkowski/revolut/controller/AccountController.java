package com.kkwiatkowski.revolut.controller;

import com.kkwiatkowski.revolut.service.AccountService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

public class AccountController {

    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Object getAccount(Request request, Response response) {
        return accountService
                .getAccount(request.queryParams("uuid"))
                .toString();
    }
}
