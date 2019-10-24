package com.kkwiatkowski.revolut.model;

import com.kkwiatkowski.revolut.model.exception.InsufficientFundsException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class AccountUnitTest {

    @InjectMocks
    private Account sut;

    @Test
    void deposits() {
        //given
        Account account = Account.builder().build();
        BigDecimal amount = BigDecimal.TEN;

        //when
        account.deposit(amount);

        //then
        assertThat(account.getBalance()).isEqualTo(amount);
    }

    @Test
    void withdraws() {
        //given
        Account account = Account.builder().balance(BigDecimal.TEN).build();
        BigDecimal amount = BigDecimal.TEN;

        //when
        account.withdraw(amount);

        //then
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void withdrawThrowsException() {
        //given
        Account account = Account.builder().balance(BigDecimal.ONE).build();
        BigDecimal amount = BigDecimal.TEN;

        //when
        ThrowableAssert.ThrowingCallable callable = () -> account.withdraw(amount);

        //then
        assertThatThrownBy(callable).isInstanceOf(InsufficientFundsException.class);
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ONE);
    }
}