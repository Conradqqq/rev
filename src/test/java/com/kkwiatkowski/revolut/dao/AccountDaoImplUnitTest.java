package com.kkwiatkowski.revolut.dao;

import com.kkwiatkowski.revolut.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountDaoImplUnitTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private AccountDaoImpl sut;

    @Test
    void gets() {
        //given
        long id = 1;
        Account account = mock(Account.class);
        when(em.find(Account.class, id)).thenReturn(account);

        //when
        Optional<Account> result = sut.get(id);

        //then
        assertThat(result).isPresent().get().isEqualTo(account);
        verify(em).find(Account.class, id);
        verifyNoMoreInteractions(em);
    }

    @Test
    void doesntFindAccount() {
        //given
        long id = 1;
        when(em.find(Account.class, id)).thenReturn(null);

        //when
        Optional<Account> result = sut.get(id);

        //then
        assertThat(result).isEmpty();
        verify(em).find(Account.class, id);
        verifyNoMoreInteractions(em);
    }

    @Test
    void merges() {
        //given
        EntityTransaction txMock = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(txMock);
        Account account = mock(Account.class);
        Account account2 = mock(Account.class);

        //when
        sut.merge(account, account2);

        //then
        InOrder inOrder = inOrder(em, txMock);
        inOrder.verify(em).getTransaction();
        inOrder.verify(txMock).begin();
        inOrder.verify(em).merge(account);
        inOrder.verify(em).merge(account2);
        inOrder.verify(em).flush();
        inOrder.verify(txMock).commit();
        inOrder.verifyNoMoreInteractions();
    }
}