package com.kkwiatkowski.revolut.service;

import com.kkwiatkowski.revolut.dao.AccountDao;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.exception.AccountNotFoundException;

import javax.inject.Inject;
import java.util.function.Supplier;

public final class AccountServiceImpl implements AccountService {

    private static final String ACCOUNT_NOT_FOUND = "Account with id %d not found";
    private final AccountDao accountDao;

    @Inject
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account getAccount(long id) {
        return accountDao.get(id)
                .orElseThrow(accountNotFoundException(id));
    }

    private Supplier<AccountNotFoundException> accountNotFoundException(long id) {
        return () -> new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND, id));
    }
}
