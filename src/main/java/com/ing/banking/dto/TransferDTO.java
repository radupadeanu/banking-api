package com.ing.banking.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferDTO {

  private String senderIban;

  private String receiverIban;

  private BigDecimal amount;

  private String currency;
}
