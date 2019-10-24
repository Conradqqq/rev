package com.kkwiatkowski.revolut.init;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerProducer {

    @Produces
    public EntityManager createEntityManager() {
        return Persistence
                .createEntityManagerFactory("rev")
                .createEntityManager();
    }

    public void close(@Disposes EntityManager entityManager) {
        entityManager.close();
    }
}
