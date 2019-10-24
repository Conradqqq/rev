package com.kkwiatkowski.revolut.controller;

import spark.Request;
import spark.Response;

public interface TransferController {

    String transferMoney(Request request, Response response);

}
