package com.ing.banking.controller;

import com.ing.banking.dto.LoanDTO;
import com.ing.banking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

  @Autowired private LoanService loanService;

  @PostMapping("/addLoan")
  public void addLoan(@RequestBody LoanDTO loanDTO) {
    loanService.createLoan(loanDTO);
  }
}
