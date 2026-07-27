package com.campus.bank_jpa_lab;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;

    public BankService(AccountRepository accountRepo, CustomerRepository customerRepo) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
    }

    // ---- mapping helper: entity -> DTO (slide 3.11) ----
    private AccountResponse toDto(Account a) {
        return new AccountResponse(a.getId(), a.getAccountNumber(), a.getType(),
                                   a.getBalance(), a.getCustomer().getName());
    }

    public AccountResponse openAccount(Long customerId, AccountType type, BigDecimal deposit) {
        Customer c = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + customerId));
        String accNo = "AC" + (100000 + new Random().nextInt(900000));
        return toDto(accountRepo.save(new Account(accNo, type, deposit, c)));
    }

    // ---- pagination + sorting (slide 3.12): Page = data + metadata ----
    public Page<AccountResponse> list(Pageable pageable) {
        return accountRepo.findAll(pageable).map(this::toDto);
    }

    public AccountResponse deposit(Long accountId, BigDecimal amount) {
        Account a = findAccount(accountId);
        a.setBalance(a.getBalance().add(amount));
        return toDto(accountRepo.save(a));
    }

    // ---- query methods, now speaking DTO to the outside ----
    public List<AccountResponse> richAccounts(BigDecimal above) {
        return accountRepo.findByBalanceGreaterThan(above).stream().map(this::toDto).toList();
    }
    public List<AccountResponse> accountsOfCustomer(Long customerId) {
        return accountRepo.findByCustomerId(customerId).stream().map(this::toDto).toList();
    }
    public BigDecimal totalOfCustomer(Long customerId) {
        return accountRepo.totalBalanceOfCustomer(customerId);
    }

    // internal helper — entities never leave the service layer
    private Account findAccount(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }

        @org.springframework.transaction.annotation.Transactional
    public String transfer(Long fromId, Long toId, BigDecimal amount) {
        Account from = findAccount(fromId);

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // STEP 1: debit — this change is written...
        from.setBalance(from.getBalance().subtract(amount));
        accountRepo.save(from);

        // STEP 2: credit — but if the destination doesn't exist,
        // findAccount THROWS here — AFTER the debit already happened!
        Account to = findAccount(toId);
        to.setBalance(to.getBalance().add(amount));
        accountRepo.save(to);

        return "Transferred " + amount + " from " + from.getAccountNumber()
                + " to " + to.getAccountNumber();
        // Any RuntimeException inside @Transactional => automatic ROLLBACK of BOTH steps
    }
}