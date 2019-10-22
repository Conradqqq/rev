package com.kkwiatkowski.revolut;

import com.kkwiatkowski.revolut.controller.AccountController;
import com.kkwiatkowski.revolut.controller.TransferController;

import javax.inject.Inject;

import static spark.Spark.*;

final class RoutingController {

    private static final String ACCOUNT_URL = "/account/:uuid";
    private static final String TRANSFER_URL = "/transfer";

    private final AccountController accountController;
    private final TransferController transferController;

    @Inject
    RoutingController(AccountController accountController, TransferController transferController) {
        this.accountController = accountController;
        this.transferController = transferController;
    }

    void setupRouting() {
        get(ACCOUNT_URL, accountController::getAccount);
        post(TRANSFER_URL, transferController::transferMoney);
    }

}
