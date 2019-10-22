package com.kkwiatkowski.revolut;

import javax.inject.Inject;

public class Application {

    @Inject
    private static final RoutingController routingController;

    public static void main(String[] args) {
        routingController.setupRouting();
    }

}
