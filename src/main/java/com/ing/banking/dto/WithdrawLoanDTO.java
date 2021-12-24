package com.ing.banking.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawLoanDTO {

  private Long id;

  private BigDecimal amount;
}
