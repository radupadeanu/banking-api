package com.ing.banking.service;

import com.ing.banking.dto.LoanDTO;
import com.ing.banking.exceptions.UndefinedAccountException;
import com.ing.banking.persistence.entity.Account;
import com.ing.banking.persistence.entity.Loan;
import com.ing.banking.persistence.entity.LoanType;
import com.ing.banking.persistence.repository.AccountRepository;
import com.ing.banking.persistence.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ing.banking.service.AccountService.UNDEFINED_ACCOUNT_MESSAGE;

@Service
public class LoanService {

  @Autowired private LoanRepository loanRepository;

  @Autowired private AccountRepository accountRepository;

  @Transactional
  public void createLoan(LoanDTO loanDTO) {
    Optional<Account> account = accountRepository.findById(loanDTO.getId());

    if (account.isPresent()) {
      Loan loan =
          Loan.builder()
              .account(account.get())
              .amount(loanDTO.getAmount())
              .startDate(loanDTO.getStartDate())
              .endDate(loanDTO.getEndDate())
              .rate(loanDTO.getRate())
              .type(LoanType.valueOf(loanDTO.getType()))
              .build();

      loanRepository.save(loan);
    } else {
      throw new UndefinedAccountException(UNDEFINED_ACCOUNT_MESSAGE);
    }
  }
}
