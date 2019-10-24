package com.kkwiatkowski.revolut.dao;

import com.kkwiatkowski.revolut.model.Transfer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferDaoImplUnitTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private TransferDaoImpl sut;

    @Test
    void merges() {
        //given
        EntityTransaction txMock = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(txMock);
        Transfer transfer = mock(Transfer.class);
        Transfer transfer2 = mock(Transfer.class);

        //when
        sut.merge(transfer, transfer2);

        //then
        InOrder inOrder = inOrder(em, txMock);
        inOrder.verify(em).getTransaction();
        inOrder.verify(txMock).begin();
        inOrder.verify(em).merge(transfer);
        inOrder.verify(em).merge(transfer2);
        inOrder.verify(em).flush();
        inOrder.verify(txMock).commit();
        inOrder.verifyNoMoreInteractions();
    }
}