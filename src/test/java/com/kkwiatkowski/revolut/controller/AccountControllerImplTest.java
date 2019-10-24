package com.kkwiatkowski.revolut.controller;

import com.google.gson.Gson;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import spark.Request;
import spark.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerImplTest {

    @Mock
    private AccountService accountService;

    @Mock
    private Gson gson;

    @InjectMocks
    private AccountControllerImpl sut;

    @Test
    void getAccount() {
        //given
        String mockedResponse = mock(String.class);
        Account account = mock(Account.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(request.queryParams(":id")).thenReturn("1");
        when(accountService.getAccount(1)).thenReturn(account);
        when(gson.toJson(account)).thenReturn(mockedResponse);

        //when
        String result = sut.getAccount(request, response);

        //then
        assertThat(result).isEqualTo(mockedResponse);
        verify(accountService).getAccount(1);
        verify(gson).toJson(account);
        verifyNoMoreInteractions(accountService, gson);
    }
}