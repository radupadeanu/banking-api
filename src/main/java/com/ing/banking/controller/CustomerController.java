package com.ing.banking.controller;

import com.ing.banking.dto.CustomerDTO;
import com.ing.banking.persistence.entity.Customer;
import com.ing.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CustomerController {

  @Autowired private CustomerService customerService;

  @GetMapping("/getCustomers")
  public List<Customer> getAllCustomers() {
    return customerService.getAllCustomersWithInactiveAccounts();
  }

  @PostMapping("/addCustomer")
  public void addCustomer(@RequestBody CustomerDTO customerDTO) {
    customerService.addCustomerWithAccounts(customerDTO);
  }

  @PutMapping("/updateCustomer")
  public void updateCustomer(@RequestBody CustomerDTO customerDTO) {
    customerService.updateCustomerInformation(customerDTO);
  }

  @GetMapping("/getSavingsAmount")
  public BigDecimal getSavingsAccountAmount(@RequestParam String ssn) {
    return customerService.getSavingsAccountAmount(ssn);
  }
}
