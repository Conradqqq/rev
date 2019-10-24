package com.kkwiatkowski.revolut.service;

import com.kkwiatkowski.revolut.dao.AccountDao;
import com.kkwiatkowski.revolut.dao.TransferDao;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.Transfer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    private static final int TIMEOUT = 1000;

    @Mock
    private AccountDao accountDao;

    @Mock
    private TransferDao transferDao;

    @InjectMocks
    private TransferServiceImpl sut;

    @Test
    void transfer() throws Exception{
        //given
        Account from = mock(Account.class);
        Account to = mock(Account.class);
        ReentrantLock fromLock = mock(ReentrantLock.class);
        ReentrantLock toLock = mock(ReentrantLock.class);
        when(from.getLock()).thenReturn(fromLock);
        when(to.getLock()).thenReturn(toLock);
        when(fromLock.tryLock(TIMEOUT, TimeUnit.MILLISECONDS)).thenReturn(true);
        when(toLock.tryLock(TIMEOUT, TimeUnit.MILLISECONDS)).thenReturn(true);

        Transfer transfer = Transfer.builder()
                .from(from)
                .to(to)
                .amount(BigDecimal.TEN)
                .build();

        //when
        sut.transfer(transfer);

        //then
        verify(fromLock).tryLock(TIMEOUT, TimeUnit.MILLISECONDS);
        verify(toLock).tryLock(TIMEOUT, TimeUnit.MILLISECONDS);
        verify(from).withdraw(transfer.getAmount());
        verify(to).deposit(transfer.getAmount());
        verify(accountDao).merge(from, to);
        verify(transferDao).merge(transfer);
        verify(fromLock).unlock();
        verify(toLock).unlock();
    }
}