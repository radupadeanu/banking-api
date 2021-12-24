package com.ing.banking.persistence.repository;

import com.ing.banking.persistence.entity.AccountStatus;
import com.ing.banking.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select distinct c from Customer c join fetch c.accounts a where a.status = :status")
    List<Customer> findAllCustomersWithInactiveAccounts(AccountStatus status);

    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Customer> findBySsn(String ssn);
}
