package com.kkwiatkowski.revolut.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

@Entity
@Data
public class Account {

    @Transient
    private ReentrantLock lock = new ReentrantLock();

    @Id
    private long id;

    @Column
    private BigDecimal balance = BigDecimal.ZERO;


}
