package com.kkwiatkowski.revolut.dao;

import com.kkwiatkowski.revolut.model.Transfer;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;

public class TransferDaoImpl implements TransferDao {

    private final EntityManager em;

    @Inject
    public TransferDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void merge(Transfer... transfers) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Arrays.asList(transfers).forEach(em::merge);
        em.flush();
        tx.commit();
    }

}
