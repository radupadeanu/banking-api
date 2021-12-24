package com.ing.banking.service;

import com.ing.banking.dto.TransferDTO;
import com.ing.banking.dto.WithdrawLoanDTO;
import com.ing.banking.exceptions.UndefinedAccountException;
import com.ing.banking.persistence.entity.Account;
import com.ing.banking.persistence.entity.Loan;
import com.ing.banking.persistence.entity.Transaction;
import com.ing.banking.persistence.repository.AccountRepository;
import com.ing.banking.persistence.repository.LoanRepository;
import com.ing.banking.persistence.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static com.ing.banking.persistence.entity.TransactionStatus.FAILED;
import static com.ing.banking.persistence.entity.TransactionStatus.SUCCESSFUL;
import static com.ing.banking.persistence.entity.TransactionType.ACCOUNTS_TRANSACTION;
import static com.ing.banking.utils.AccountUtils.generateIban;
import static java.math.BigDecimal.ONE;

@Service
public class AccountService {

  public static final String UNDEFINED_ACCOUNT_MESSAGE = "Account does not exist";
  public static final String SUCCESSFUL_MESSAGE = "SUCCESSFUL";

  @Autowired private AccountRepository accountRepository;
  @Autowired private TransactionRepository transactionRepository;
  @Autowired private LoanRepository loanRepository;

  public Account generateAccount(String type) {
    return Account.builder().iban(generateIban()).type(type).build();
  }

  @Transactional
  public ResponseEntity<String> sendAmount(TransferDTO transferDTO) {
    Account senderAccount = accountRepository.findAccountByIban(transferDTO.getSenderIban());
    Account receiverAccount = accountRepository.findAccountByIban(transferDTO.getReceiverIban());

    Set<Transaction> transactions = new HashSet<>();

    Transaction currentTransaction = createTransaction(transferDTO);

    if (senderAccount.getAmount().compareTo(transferDTO.getAmount()) >= 0) {

      senderAccount.setTransactions(transactions);
      senderAccount.addTransaction(currentTransaction);

      BigDecimal senderBalance = senderAccount.getAmount().subtract(transferDTO.getAmount());
      BigDecimal receiverBalance = receiverAccount.getAmount().add(transferDTO.getAmount());
      senderAccount.setAmount(senderBalance);
      receiverAccount.setAmount(receiverBalance);

      currentTransaction.setStatus(SUCCESSFUL);
    } else {
      currentTransaction.setStatus(FAILED);
    }

    accountRepository.saveAll(Arrays.asList(senderAccount, receiverAccount));

    return new ResponseEntity<>(currentTransaction.getStatus().name(), HttpStatus.OK);
  }

  private Transaction createTransaction(TransferDTO transferDTO) {
    return Transaction.builder()
        .amount(transferDTO.getAmount())
        .senderIban(transferDTO.getSenderIban())
        .receiverIban(transferDTO.getReceiverIban())
        .currency(transferDTO.getCurrency())
        .type(ACCOUNTS_TRANSACTION)
        .build();
  }

  public ResponseEntity<String> applyReduction(WithdrawLoanDTO withdrawLoanDTO) {
    Optional<Account> account = accountRepository.findById(withdrawLoanDTO.getId());

    if (account.isPresent()) {
      List<Transaction> transactions = transactionRepository.findAllByAccount(account.get());
      boolean exceedsAmount =
          transactions.stream()
              .anyMatch(
                  transaction ->
                      transaction.getAmount().compareTo(withdrawLoanDTO.getAmount()) > 0);

      if (LocalDate.now().getMonth().equals(Month.DECEMBER) && exceedsAmount) {
        List<Loan> existingLoans = loanRepository.findAllByAccount(account.get());

        Loan highestRateLoan =
            existingLoans.stream()
                .max(Comparator.comparing(Loan::getRate))
                .orElseThrow(NoSuchElementException::new);
        BigDecimal highestRate = highestRateLoan.getRate();
        highestRateLoan.setRate(highestRate.subtract(ONE));

        loanRepository.save(highestRateLoan);
      }
    } else {
      throw new UndefinedAccountException(UNDEFINED_ACCOUNT_MESSAGE);
    }
    return new ResponseEntity<>(SUCCESSFUL_MESSAGE, HttpStatus.OK);
  }
}
