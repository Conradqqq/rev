package com.kkwiatkowski.revolut.dao;

import com.kkwiatkowski.revolut.model.Account;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final EntityManager em;

    @Inject
    public AccountDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Account> get(long id) {
        Account account = em.find(Account.class, id);
        em.detach(account);
        return Optional.ofNullable(account);
    }

    @Override
    public void merge(Account... accounts) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Arrays.asList(accounts).forEach(em::merge);
        em.flush();
        tx.commit();
    }
}
