package com.kkwiatkowski.revolut.controller;

import com.google.gson.Gson;
import com.kkwiatkowski.revolut.controller.validator.RequestValidator;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.Transfer;
import com.kkwiatkowski.revolut.model.dto.request.TransferDto;
import com.kkwiatkowski.revolut.service.AccountService;
import com.kkwiatkowski.revolut.service.TransferService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

public final class TransferControllerImpl implements TransferController {

    private final RequestValidator<Transfer> requestValidator;
    private final AccountService accountService;
    private final TransferService transferService;
    private final Gson gson;

    @Inject
    public TransferControllerImpl(RequestValidator<Transfer> requestValidator, AccountService accountService,
                                  TransferService transferService, Gson gson) {
        this.requestValidator = requestValidator;
        this.accountService = accountService;
        this.transferService = transferService;
        this.gson = gson;
    }

    public String transferMoney(Request request, Response response) {
        TransferDto transferDto = gson.fromJson(request.body(), TransferDto.class);

        Account fromAccount = accountService.getAccount(transferDto.getFrom());
        Account toAccount = accountService.getAccount(transferDto.getTo());

        Transfer transfer = Transfer.builder()
                .to(toAccount)
                .from(fromAccount)
                .amount(transferDto.getAmount())
                .build();

        requestValidator.validate(transfer);
        transferService.transfer(transfer);

        response.status(204);
        return "";
    }
}
