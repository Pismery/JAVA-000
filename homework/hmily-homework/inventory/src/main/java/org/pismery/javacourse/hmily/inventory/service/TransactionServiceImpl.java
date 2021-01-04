/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pismery.javacourse.hmily.inventory.service;

import org.dromara.hmily.annotation.HmilyTCC;
import org.pismery.javacourse.hmily.account.api.Account;
import org.pismery.javacourse.hmily.account.api.AccountService;
import org.pismery.javacourse.hmily.account.api.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lw1243925457
 */
@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    // @Autowired(required = false)
    private AccountService accountService;

    /**
     * 这个注入很关键，这样注入就能进入RPC的切面，没有就报错
     * @param accountService account service
     */
    @Autowired(required = false)
    public TransactionServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void transactionException() {
        log.info("============dubbo tcc 执行 Try transactionException ===============");
        payA();
        payException();
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void transaction() {
        log.info("============dubbo tcc 执行 Try transaction ===============");

        payA();
        payB();
    }

    private void payA() {
        log.info("============py A dubbo try 执行确认付款接口===============");
        Account account = new Account();
        account.setId(1L);
        account.setUsWallet(-1L);
        account.setCnyWallet(7L);
        accountService.paySuccess(account);
    }

    private void payException() {
        log.info("============py exception dubbo try 执行确认付款接口===============");
        Account account = new Account();
        account.setId(2L);
        account.setUsWallet(1L);
        account.setCnyWallet(-7L);
        accountService.payException();
    }

    private void payB() {
        log.info("============py B dubbo try 执行确认付款接口===============");
        Account account = new Account();
        account.setId(2L);
        account.setUsWallet(1L);
        account.setCnyWallet(-7L);
        accountService.paySuccess(account);
    }

    public void confirmOrderStatus() {
        log.info("=========进行订单 confirm 操作完成================");
    }

    public void cancelOrderStatus() {
        log.info("=========进行订单 cancel 操作完成================");
    }
}
