package com.kkwiatkowski.revolut.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Transfer {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private BigDecimal amount;

    @ManyToOne
    private Account from;

    @ManyToOne
    private Account to;

}
