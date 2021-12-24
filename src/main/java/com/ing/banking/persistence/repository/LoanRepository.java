package com.ing.banking.persistence.repository;

import com.ing.banking.persistence.entity.Account;
import com.ing.banking.persistence.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByAccount(Account account);
}
