package org.pismery.javacourse.hmily.account.api;


import org.dromara.hmily.annotation.Hmily;

public interface AccountService {

    /**
     * 美元账户和人民币账户交易
     * @param account account
     * @return bool
     */
    @Hmily
    boolean pay(Account account);
}
