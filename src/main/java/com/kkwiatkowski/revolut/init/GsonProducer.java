package com.kkwiatkowski.revolut.init;

import com.google.gson.Gson;

import javax.enterprise.inject.Produces;

public class GsonProducer {

    @Produces
    public Gson gson() {
        return new Gson();
    }

}
