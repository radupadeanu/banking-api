package com.ing.banking.exceptions;

public class UndefinedAccountException extends RuntimeException {
  public UndefinedAccountException(String message) {
    super(message);
  }
}
