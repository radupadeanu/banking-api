package com.ing.banking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

  private String addressLine;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private String ssn;

  private String accountType;
}
