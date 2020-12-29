package org.pismery.javacourse.hmily.inventory.controller;

import org.pismery.javacourse.hmily.inventory.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public void transaction() {
        transactionService.transaction();
    }

}
