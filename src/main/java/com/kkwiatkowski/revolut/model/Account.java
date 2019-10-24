package com.kkwiatkowski.revolut.model;

import com.kkwiatkowski.revolut.model.exception.InsufficientFundsException;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Account {

    private static final String INSUFFICIENT_FUNDS = "Insufficient funds for account id %d";

    @Transient
    private final transient ReentrantLock lock = new ReentrantLock();

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(String.format(INSUFFICIENT_FUNDS, id));
        }
        balance = balance.subtract(amount);
    }

}
