package com.kkwiatkowski.revolut;

import com.kkwiatkowski.revolut.init.AppInit;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public final class App {

    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        container.instance().select(AppInit.class).get().initialize();
        weld.shutdown();
    }

}
