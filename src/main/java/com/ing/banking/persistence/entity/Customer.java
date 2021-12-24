package com.ing.banking.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

/** @author Radu Padeanu
 * Customer Entity - general information */

@Entity
@Table(name = "customers")
@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder
public class Customer extends BaseEntity {

  @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private Set<Account> accounts;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "ssn")
  private String ssn;

  @Column(name = "address_line")
  private String addressLine;

  @Column(name = "phone_number")
  private String phoneNumber;

  public void addAccount(Account account) {
    accounts.add(account);
    account.setCustomer(this);
  }
}
