package com.kkwiatkowski.revolut.init;

import javax.inject.Inject;

public final class AppInit {

    private final RoutingInit routingInit;
    private final DataMock dataMock;

    @Inject
    AppInit(RoutingInit routingInit, DataMock dataMock) {
        this.routingInit = routingInit;
        this.dataMock = dataMock;
    }

    public void initialize() {
        routingInit.initRouting();
        dataMock.mockData();
    }
}
