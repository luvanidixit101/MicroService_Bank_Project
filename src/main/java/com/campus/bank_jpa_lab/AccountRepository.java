package com.campus.bank_jpa_lab;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // ---- derived queries: Spring reads the METHOD NAME and builds SQL ----
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByType(AccountType type);
    List<Account> findByBalanceGreaterThan(BigDecimal amount);
    List<Account> findByCustomerId(Long customerId);           // walks the relationship!
    List<Account> findByTypeOrderByBalanceDesc(AccountType type);

    // ---- @Query: custom JPQL (entity + FIELD names, not table/columns) ----
    @Query("SELECT COALESCE(SUM(a.balance), 0) FROM Account a WHERE a.customer.id = :cid")
    BigDecimal totalBalanceOfCustomer(@Param("cid") Long customerId);
}