package com.ing.banking.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
  CHECKING("CHECKING"),
  SAVINGS("SAVINGS"),
  FIXED_DEPOSIT("FIXED DEPOSIT");

  private String accountType;
}
