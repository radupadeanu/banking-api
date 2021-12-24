package com.ing.banking.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.EnumType.STRING;

/**
 * @author Radu Padeanu
 * Transaction Entity - used for different actions, within or between accounts
 */

@Entity
@Table(name = "transactions")
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Transaction extends BaseEntity{

    @Enumerated(STRING)
    @Column(name = "type")
    private TransactionType type;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Enumerated(STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "sender_iban")
    private String senderIban;

    @Column(name = "receiver_iban")
    private String receiverIban;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

}

