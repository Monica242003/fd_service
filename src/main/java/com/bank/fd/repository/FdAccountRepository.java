package com.bank.fd.repository;

import com.bank.fd.entity.FdAccount;
import com.bank.fd.model.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FdAccountRepository extends JpaRepository<FdAccount, Long> {
    List<FdAccount> findByCustomerId(String customerId);
    List<FdAccount> findByStatus(AccountStatus status);
    List<FdAccount> findByCustomerIdAndStatus(String customerId, AccountStatus status);
}
