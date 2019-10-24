package com.kkwiatkowski.revolut.init;

import spark.Request;
import spark.Response;

public interface ExceptionHandler {

    void handleException(Exception exception, Request request, Response response);

}
