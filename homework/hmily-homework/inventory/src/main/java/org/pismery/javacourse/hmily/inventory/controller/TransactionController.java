package org.pismery.javacourse.hmily.inventory.controller;

import org.pismery.javacourse.hmily.account.api.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction")
    public void transaction() {
        transactionService.transaction();
    }

    @GetMapping("/transactionException")
    public void transactionException() {
        transactionService.transactionException();
    }

}
