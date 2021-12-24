package com.ing.banking.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

/**
 * @author Radu Padeanu
 * Loan Entity - Many-to-one relationship with accounts
 */

@Entity
@Table(name = "loans")
@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder
public class Loan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "rate")
    private BigDecimal rate;

    @Enumerated(STRING)
    @Column(name = "type")
    private LoanType type;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

}
