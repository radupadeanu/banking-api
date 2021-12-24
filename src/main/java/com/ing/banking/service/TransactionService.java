package com.ing.banking.service;

import com.ing.banking.exceptions.UndefinedCustomerException;
import com.ing.banking.persistence.entity.Account;
import com.ing.banking.persistence.entity.Customer;
import com.ing.banking.persistence.entity.Transaction;
import com.ing.banking.persistence.repository.CustomerRepository;
import com.ing.banking.persistence.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ing.banking.persistence.entity.TransactionStatus.PENDING;
import static com.ing.banking.service.CustomerService.CUSTOMER_MESSAGE;

@Service
public class TransactionService {

  @Autowired private TransactionRepository transactionRepository;

  @Autowired private CustomerRepository customerRepository;

  public List<Transaction> getTransactionsWithExceededAmountAndCustomers(BigDecimal amount) {
    return transactionRepository.getTransactionsWithExceededAmount(amount);
  }

  public List<Transaction> getPendingTransactions(String firstName, String lastName) {
    Optional<Customer> customer =
        customerRepository.findByFirstNameAndLastName(firstName, lastName);

    if (customer.isEmpty()) {
      throw new UndefinedCustomerException(CUSTOMER_MESSAGE);
    }

    List<Transaction> pendingTransactions = new ArrayList<>();

    Set<Account> customerAccounts = customer.get().getAccounts();
    for (Account customerAccount : customerAccounts) {
      pendingTransactions.add(
          transactionRepository.findByAccountAndStatus(customerAccount, PENDING));
    }

    return pendingTransactions;
  }
}
