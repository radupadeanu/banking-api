package com.ing.banking.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

import static com.ing.banking.persistence.entity.AccountStatus.ACTIVE;
import static java.math.BigDecimal.ZERO;
import static javax.persistence.EnumType.STRING;

/** @author Radu Padeanu
 * Account Entity - Many-to-one relationship with customer */

@Entity
@Table(name = "accounts")
@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder
public class Account extends BaseEntity {

  public static final String RON_CURRENCY = "RON";
  public static final String EUR_CURRENCY = "EUR";
  private static final String USD_CURRENCY = "USD";

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;

  @OneToMany(
      mappedBy = "account",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<Loan> loans;

  @OneToMany(
      mappedBy = "account",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<Transaction> transactions;

  @Column(name = "iban")
  private String iban;

  @Column(name = "type")
  private String type;

  @Column(name = "amount")
  @Builder.Default
  private BigDecimal amount = ZERO;

  @Column(name = "currency")
  @Builder.Default
  private String currency = RON_CURRENCY;

  @Enumerated(STRING)
  @Column(name = "status")
  @Builder.Default
  private AccountStatus status = ACTIVE;

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
    transaction.setAccount(this);
  }
}
