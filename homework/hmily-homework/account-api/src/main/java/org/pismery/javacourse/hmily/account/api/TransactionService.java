package org.pismery.javacourse.hmily.account.api;

import org.dromara.hmily.annotation.Hmily;

public interface TransactionService {
    void transaction();

    @Hmily
    void transactionException();
}
