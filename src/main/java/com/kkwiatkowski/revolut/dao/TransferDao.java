package com.kkwiatkowski.revolut.dao;

import com.kkwiatkowski.revolut.model.Transfer;

public interface TransferDao {

    void merge(Transfer... transfers);

}
