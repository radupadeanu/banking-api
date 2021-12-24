package com.ing.banking.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType {
  ACCOUNTS_TRANSACTION("ACCOUNTS TRANSACTION"),
  WITHDRAW_TRANSACTION("WITHDRAW TRANSACTION"),
  DEPOSIT_TRANSACTION("DEPOSIT TRANSACTION"),
  LOAN_TRANSACTION("LOAN TRANSACTION");

  private String transactionType;
}
