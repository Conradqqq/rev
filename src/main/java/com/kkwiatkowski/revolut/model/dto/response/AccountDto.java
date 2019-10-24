package com.kkwiatkowski.revolut.model.dto.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class AccountDto {
    long id;
    BigDecimal balance;
}
