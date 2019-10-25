package com.kkwiatkowski.revolut.controller;

import com.google.gson.Gson;
import com.kkwiatkowski.revolut.controller.validator.RequestValidator;
import com.kkwiatkowski.revolut.model.Account;
import com.kkwiatkowski.revolut.model.dto.request.TransferDto;
import com.kkwiatkowski.revolut.service.AccountService;
import com.kkwiatkowski.revolut.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferControllerImplTest {

    private static final int NO_CONTENT_STATUS = 204;

    private final Gson gson = new Gson();

    @Mock
    private RequestValidator<TransferDto> requestValidator;

    @Mock
    private AccountService accountService;

    @Mock
    private TransferService transferService;


    private TransferControllerImpl sut;

    @BeforeEach
    void setUp() {
        sut = new TransferControllerImpl(requestValidator, accountService, transferService, gson);
    }

    @Test
    void transferMoney() {
        //given
        Account account = mock(Account.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        TransferDto transferDto = new TransferDto(1, 2, BigDecimal.TEN);
        when(request.body()).thenReturn(gson.toJson(transferDto));
        when(accountService.getAccount(1)).thenReturn(account);
        when(accountService.getAccount(2)).thenReturn(account);

        //when
        String result = sut.transferMoney(request, response);

        //then
        assertThat(result).isEmpty();
        verify(requestValidator).validate(transferDto);
        verify(accountService).getAccount(1);
        verify(accountService).getAccount(2);
        verify(transferService).transfer(any());
        verify(response).status(NO_CONTENT_STATUS);
        verifyNoMoreInteractions(accountService);
    }
}