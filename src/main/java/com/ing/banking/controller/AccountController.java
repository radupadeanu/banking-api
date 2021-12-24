package com.ing.banking.controller;

import com.ing.banking.dto.TransferDTO;
import com.ing.banking.dto.WithdrawLoanDTO;
import com.ing.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  @Autowired private AccountService accountService;

  @PutMapping("/sendAmount")
  public ResponseEntity<String> sendAmount(@RequestBody TransferDTO transferDTO) {
    return accountService.sendAmount(transferDTO);
  }

  // should be scheduled only before December during the entire loan period
  @PutMapping("/applyRateReduction")
  public ResponseEntity<String> applyRateReduction(@RequestBody WithdrawLoanDTO withdrawLoanDTO) {
    return accountService.applyReduction(withdrawLoanDTO);
  }
}
