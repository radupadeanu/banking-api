package com.ing.banking.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountStatus {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE");

  private String accountStatus;
}
