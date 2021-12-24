package com.ing.banking.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoanType {
    PERSONAL_LOAN("PERSONAL"),
    BUSINESS_LOAN("BUSINESS"),
    HOME_LOAN("HOME"),
    CAR_LOAN("CAR");

    private String loanType;
}
