package com.ing.banking.exceptions;

public class UndefinedCustomerException extends RuntimeException {
  public UndefinedCustomerException(String message) {
    super(message);
  }
}
