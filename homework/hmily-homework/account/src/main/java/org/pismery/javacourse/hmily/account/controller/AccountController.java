package org.pismery.javacourse.hmily.account.controller;

import org.pismery.javacourse.hmily.account.api.Account;
import org.pismery.javacourse.hmily.account.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping
    public void transaction() {
        accountService.paySuccess(new Account());
    }
}
