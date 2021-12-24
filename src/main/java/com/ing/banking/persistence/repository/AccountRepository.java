package com.ing.banking.persistence.repository;

import com.ing.banking.persistence.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findAccountByIban(String iban);
}
