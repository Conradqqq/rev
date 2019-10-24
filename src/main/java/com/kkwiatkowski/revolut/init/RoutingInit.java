package com.kkwiatkowski.revolut.init;

import com.kkwiatkowski.revolut.controller.AccountController;
import com.kkwiatkowski.revolut.controller.TransferController;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

import static spark.Spark.*;

@Slf4j
final class RoutingInit {

    private static final String ACCOUNT_URL = "/account/:id";
    private static final String TRANSFER_URL = "/transfer";

    private final AccountController accountController;
    private final TransferController transferController;
    private final ExceptionHandler exceptionHandler;

    @Inject
    RoutingInit(AccountController accountController, TransferController transferController,
                ExceptionHandler exceptionHandler) {
        this.accountController = accountController;
        this.transferController = transferController;
        this.exceptionHandler = exceptionHandler;
    }

    void initRouting() {
        before(this::beforeRequestFilter);
        get(ACCOUNT_URL, accountController::getAccount);
        post(TRANSFER_URL, transferController::transferMoney);
        exception(Exception.class, exceptionHandler::handleException);
    }

    private void beforeRequestFilter(Request request, Response response) {
        response.type("application/json");
        System.out.println(String.format("Request: %s", request.body()));
    }

}
