package com.kkwiatkowski.revolut.model.dto.request;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class TransferDto {
    long from;
    long to;
    BigDecimal amount;
}
