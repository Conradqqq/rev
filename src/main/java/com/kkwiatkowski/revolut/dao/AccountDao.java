package com.kkwiatkowski.revolut.dao;

import com.kkwiatkowski.revolut.model.Account;

import java.util.Optional;

public interface AccountDao {

    Optional<Account> get(long id);

    void merge(Account... accounts);

}
