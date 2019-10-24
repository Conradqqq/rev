package com.kkwiatkowski.revolut.service;

import com.kkwiatkowski.revolut.dao.AccountDao;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.exception.AccountNotFoundException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplUnitTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountServiceImpl sut;

    @Test
    void throwsException() {
        //given
        long id = 1;
        when(accountDao.get(id)).thenReturn(Optional.empty());

        //when
        ThrowableAssert.ThrowingCallable callable = () -> sut.getAccount(id);

        //then
        assertThatThrownBy(callable)
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining(String.valueOf(id));
        verify(accountDao).get(id);
        verifyNoMoreInteractions(accountDao);
    }

}
