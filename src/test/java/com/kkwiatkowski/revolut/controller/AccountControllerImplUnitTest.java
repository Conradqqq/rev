package com.kkwiatkowski.revolut.controller;

import com.google.gson.Gson;
import com.kkwiatkowski.revolut.controller.validator.RequestValidator;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.dto.response.AccountDto;
import com.kkwiatkowski.revolut.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerImplUnitTest {

    private final Gson gson = new Gson();

    @Mock
    private RequestValidator<String> requestValidator;

    @Mock
    private AccountService accountService;

    private AccountControllerImpl sut;

    @BeforeEach
    void setUp() {
        sut = new AccountControllerImpl(requestValidator, accountService, gson);
    }

    @Test
    void getAccount() {
        //given
        Account account = mock(Account.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        AccountDto accountDto = AccountDto.builder().id(1).balance(BigDecimal.TEN).build();
        when(request.params(":id")).thenReturn("1");
        when(accountService.getAccount(1)).thenReturn(account);
        when(account.getId()).thenReturn(1L);
        when(account.getBalance()).thenReturn(BigDecimal.TEN);

        //when
        String result = sut.getAccount(request, response);

        //then
        assertThat(result).isEqualTo(gson.toJson(accountDto));
        verify(accountService).getAccount(1);
        verifyNoMoreInteractions(accountService);
    }
}