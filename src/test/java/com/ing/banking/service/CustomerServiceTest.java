package com.ing.banking.service;

import com.ing.banking.dto.CustomerDTO;
import com.ing.banking.persistence.entity.Account;
import com.ing.banking.persistence.entity.AccountStatus;
import com.ing.banking.persistence.entity.Customer;
import com.ing.banking.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock private AccountService accountService;
  @Mock private CustomerRepository customerRepository;
  private CustomerService classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new CustomerService(accountService, customerRepository);
  }

  @Test
  void addCustomerWithAccounts() {
    // GIVEN + WHEN
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setAccountType("CHECKING");

    Set<Account> accounts = new HashSet<>();
    Account account = new Account();
    Customer customer = new Customer();

    accounts.add(account);
    customer.setAccounts(accounts);

    when(accountService.generateAccount(customerDTO.getAccountType())).thenReturn(account);

    // THEN
    classUnderTest.addCustomerWithAccounts(customerDTO);

    verify(customerRepository).save(customer);
  }

  @Test
  void getAllCustomersWithInactiveAccounts() {
    // GIVEN + WHEN
    List<Customer> customers = new ArrayList<>();

    when(customerRepository.findAllCustomersWithInactiveAccounts(AccountStatus.INACTIVE))
        .thenReturn(customers);

    classUnderTest.getAllCustomersWithInactiveAccounts();

    // THEN
    verify(customerRepository).findAllCustomersWithInactiveAccounts(any());
  }

  @Test
  void updateCustomerInformation() {
    // GIVEN + WHEN
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setSsn(anyString());

    Optional<Customer> customer = Optional.of(new Customer());

    when(customerRepository.findBySsn(customerDTO.getSsn())).thenReturn(customer);

    classUnderTest.updateCustomerInformation(customerDTO);

    // THEN
    verify(customerRepository).findBySsn(anyString());
  }

  @Test
  void getSavingsAccountAmount() {
    String ssn = anyString();
    Set<Account> accounts = new HashSet<>();
    Account account = new Account();
    account.setAmount(BigDecimal.valueOf(100));
    account.setType("SAVINGS");
    accounts.add(account);

    Optional<Customer> customer = Optional.of(new Customer());
    customer.get().setAccounts(accounts);

    when(customerRepository.findBySsn(ssn)).thenReturn(customer);

    classUnderTest.getSavingsAccountAmount(ssn);

    verify(customerRepository).findBySsn(ssn);
    assertNotNull(customer);
    assertEquals(BigDecimal.valueOf(100), customer.get().getAccounts().iterator().next().getAmount());
  }
}
