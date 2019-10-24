package com.kkwiatkowski.revolut.model.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorDto {
    String message;
    String details;
}
