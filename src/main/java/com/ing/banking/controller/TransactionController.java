package com.ing.banking.controller;

import com.ing.banking.persistence.entity.Transaction;
import com.ing.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TransactionController {

  @Autowired private TransactionService transactionService;

  @GetMapping("/getTransactionsWithAmount")
  public List<Transaction> getTransactionsWithExceededAmountAndCustomers(
      @RequestParam BigDecimal amount) {
    return transactionService.getTransactionsWithExceededAmountAndCustomers(amount);
  }

  @GetMapping("/getPendingTransactions")
  public List<Transaction> getAccountPendingTransactions(
      @RequestParam String customerFirstName, @RequestParam String customerLastName) {
    return transactionService.getPendingTransactions(customerFirstName, customerLastName);
  }
}
