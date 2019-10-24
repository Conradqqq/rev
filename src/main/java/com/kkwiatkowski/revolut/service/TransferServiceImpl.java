package com.kkwiatkowski.revolut.service;

import com.kkwiatkowski.revolut.dao.AccountDao;
import com.kkwiatkowski.revolut.dao.TransferDao;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.Transfer;
import com.kkwiatkowski.revolut.model.exception.InternalServerException;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public final class TransferServiceImpl implements TransferService {

    private static final int TIMEOUT = 1000;
    private final AccountDao accountDao;
    private final TransferDao transferDao;

    @Inject
    public TransferServiceImpl(AccountDao accountDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    @Override
    public void transfer(Transfer transfer) {
        Account from = transfer.getFrom();
        Account to = transfer.getTo();
        try {
            boolean fromLock = from.getLock().tryLock(TIMEOUT, TimeUnit.MILLISECONDS);
            if (fromLock) {
                boolean toLock = to.getLock().tryLock(TIMEOUT, TimeUnit.MILLISECONDS);

                if (toLock) {
                    from.withdraw(transfer.getAmount());
                    to.deposit(transfer.getAmount());

                    accountDao.merge(from, to);
                    transferDao.merge(transfer);
                }
            }
        } catch (InterruptedException e) {
            throw new InternalServerException("Interrupted exception during transfer", e);
        } finally {
            from.getLock().unlock();
            to.getLock().unlock();
        }
    }
}
