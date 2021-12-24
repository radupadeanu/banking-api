package com.ing.banking.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionStatus {
    PENDING("PENDING"),
    SUCCESSFUL("SUCCESSFUL"),
    FAILED("FAILED");

    private String transactionStatus;
}
