package com.kkwiatkowski.revolut.controller;

import com.google.gson.Gson;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.dto.response.AccountDto;
import com.kkwiatkowski.revolut.service.AccountService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

public final class AccountControllerImpl implements AccountController {

    private final AccountService accountService;
    private final Gson gson;

    @Inject
    public AccountControllerImpl(AccountService accountService, Gson gson) {
        this.accountService = accountService;
        this.gson = gson;
    }

    public String getAccount(Request request, Response response) {
        long accountid = Long.parseLong(request.params(":id"));
        Account account = accountService.getAccount(accountid);
        AccountDto accountDto = AccountDto.builder()
                .id(accountid)
                .balance(account.getBalance())
                .build();
        return gson.toJson(accountDto);
    }
}
