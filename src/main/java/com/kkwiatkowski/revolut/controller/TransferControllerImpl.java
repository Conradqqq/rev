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

    private static final int NO_CONTENT_STATUS = 204;
    private final RequestValidator<TransferDto> requestValidator;
    private final AccountService accountService;
    private final TransferService transferService;
    private final Gson gson;

    @Inject
    public TransferControllerImpl(RequestValidator<TransferDto> requestValidator, AccountService accountService,
                                  TransferService transferService, Gson gson) {
        this.requestValidator = requestValidator;
        this.accountService = accountService;
        this.transferService = transferService;
        this.gson = gson;
    }

    public String transferMoney(Request request, Response response) {
        TransferDto transferDto = gson.fromJson(request.body(), TransferDto.class);
        requestValidator.validate(transferDto);

        Account fromAccount = accountService.getAccount(transferDto.getFrom());
        Account toAccount = accountService.getAccount(transferDto.getTo());

        Transfer transfer = Transfer.builder()
                .to(toAccount)
                .from(fromAccount)
                .amount(transferDto.getAmount())
                .build();

        transferService.transfer(transfer);

        response.status(NO_CONTENT_STATUS);
        return "";
    }
}
