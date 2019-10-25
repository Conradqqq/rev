package com.kkwiatkowski.revolut.controller.validator;

import com.kkwiatkowski.revolut.model.exception.RequestValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountRequestValidatorUnitTest {

    private AccountRequestValidator sut = new AccountRequestValidator();

    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of("sdf", "number"),
                Arguments.of("-1", "above 0")
        );
    }

    @ParameterizedTest(name = "{index} => accountId \"{0}\" have to be {1}")
    @MethodSource("parameters")
    void validateThrows(String accountId, String message) {
        assertThatThrownBy(() -> sut.validate(accountId))
                .isInstanceOf(RequestValidationException.class)
                .hasMessageContaining(message);
    }

    @Test
    void validateSuccess() {
        assertThatCode(() -> sut.validate("1"))
                .doesNotThrowAnyException();
    }
}