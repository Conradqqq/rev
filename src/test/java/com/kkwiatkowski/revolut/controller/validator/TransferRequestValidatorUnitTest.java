package com.kkwiatkowski.revolut.controller.validator;

import com.kkwiatkowski.revolut.model.dto.request.TransferDto;
import com.kkwiatkowski.revolut.model.exception.RequestValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransferRequestValidatorUnitTest {

    private TransferRequestValidator sut = new TransferRequestValidator();


    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(new TransferDto(1, 1, BigDecimal.TEN), "accounts have to be different"),
                Arguments.of(new TransferDto(1, 2, BigDecimal.ZERO), "Amount have to be greater than 0"),
                Arguments.of(new TransferDto(1, 2, new BigDecimal(-10)), "Amount have to be greater than 0")
        );
    }

    @ParameterizedTest(name = "{index} => \"{0}\" {1}")
    @MethodSource("parameters")
    void validateThrows(TransferDto transfer, String message) {
        assertThatThrownBy(() -> sut.validate(transfer))
                .isInstanceOf(RequestValidationException.class)
                .hasMessageContaining(message);
    }

    @Test
    void validateSuccess() {
        assertThatCode(() -> sut.validate(new TransferDto(1, 2, BigDecimal.TEN)))
                .doesNotThrowAnyException();
    }
}