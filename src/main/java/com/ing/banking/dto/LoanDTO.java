package com.ing.banking.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanDTO {

  private Long id;

  private BigDecimal rate;

  private BigDecimal amount;

  private String type;

  private LocalDate startDate;

  private LocalDate endDate;
}
