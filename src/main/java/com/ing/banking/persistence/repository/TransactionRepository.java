package com.ing.banking.persistence.repository;

import com.ing.banking.persistence.entity.Account;
import com.ing.banking.persistence.entity.Transaction;
import com.ing.banking.persistence.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.amount > :amount")
    List<Transaction> getTransactionsWithExceededAmount(BigDecimal amount);

    List<Transaction> findAllByAccount(Account account);

    Transaction findByAccountAndStatus(Account account, TransactionStatus status);

}
