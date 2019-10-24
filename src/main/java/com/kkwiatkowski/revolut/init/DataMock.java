package com.kkwiatkowski.revolut.init;

import com.kkwiatkowski.revolut.model.Account;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;

public class DataMock {

    private final EntityManager em;

    @Inject
    public DataMock(EntityManager em) {
        this.em = em;
    }

    void mockData() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(Account.builder().balance(new BigDecimal("100")).build());
        em.persist(Account.builder().balance(new BigDecimal("200")).build());
        em.flush();
        tx.commit();
    }
}
