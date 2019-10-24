package com.kkwiatkowski.revolut.controller;

import spark.Request;
import spark.Response;

public interface AccountController {

    String getAccount(Request request, Response response);

}
