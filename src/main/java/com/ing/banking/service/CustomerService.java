package com.ing.banking.service;

import com.ing.banking.dto.CustomerDTO;
import com.ing.banking.exceptions.UndefinedAccountException;
import com.ing.banking.exceptions.UndefinedCustomerException;
import com.ing.banking.persistence.entity.Account;
import com.ing.banking.persistence.entity.Customer;
import com.ing.banking.persistence.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ing.banking.persistence.entity.AccountStatus.INACTIVE;
import static com.ing.banking.persistence.entity.AccountType.SAVINGS;
import static com.ing.banking.service.AccountService.UNDEFINED_ACCOUNT_MESSAGE;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerService {

  public static final String CUSTOMER_MESSAGE = "Customer does not exist";

  @Autowired private AccountService accountService;

  @Autowired private CustomerRepository customerRepository;

  @Transactional
  public void addCustomerWithAccounts(CustomerDTO customerDTO) {
    Set<Account> accounts = new HashSet<>();
    Account account = accountService.generateAccount(customerDTO.getAccountType());
    accounts.add(account);

    Customer customer = generateCustomer(customerDTO, accounts);
    customer.addAccount(account);

    customerRepository.save(customer);
    accounts.clear();
  }

  private Customer generateCustomer(CustomerDTO customerDTO, Set<Account> accounts) {

    return Customer.builder()
        .addressLine(customerDTO.getAddressLine())
        .accounts(accounts)
        .firstName(customerDTO.getFirstName())
        .lastName(customerDTO.getLastName())
        .phoneNumber(customerDTO.getPhoneNumber())
        .ssn(customerDTO.getSsn())
        .build();
  }

  public List<Customer> getAllCustomersWithInactiveAccounts() {
    return customerRepository.findAllCustomersWithInactiveAccounts(INACTIVE);
  }

  public void updateCustomerInformation(CustomerDTO customerDTO) {
    Optional<Customer> customer = customerRepository.findBySsn(customerDTO.getSsn());

    if (customer.isEmpty()) {
      throw new UndefinedCustomerException(CUSTOMER_MESSAGE);
    }

    updateAndPersistCustomer(customer.get(), customerDTO);
  }

  private void updateAndPersistCustomer(Customer customer, CustomerDTO customerDTO) {

    customer.setFirstName(customerDTO.getFirstName());
    customer.setLastName(customerDTO.getLastName());
    customer.setAddressLine(customerDTO.getAddressLine());
    customer.setPhoneNumber(customerDTO.getPhoneNumber());

    customerRepository.save(customer);
  }

  public BigDecimal getSavingsAccountAmount(String ssn) {
    Optional<Customer> customer = customerRepository.findBySsn(ssn);

    if (customer.isEmpty()) {
      throw new UndefinedCustomerException(CUSTOMER_MESSAGE);
    }

    Optional<Account> savingsAccount =
        customer.get().getAccounts().stream()
            .filter(account -> account.getType().equalsIgnoreCase(SAVINGS.name()))
            .findFirst();

    if (savingsAccount.isEmpty()) {
      throw new UndefinedAccountException(UNDEFINED_ACCOUNT_MESSAGE);
    }

    return savingsAccount.get().getAmount();
  }
}
