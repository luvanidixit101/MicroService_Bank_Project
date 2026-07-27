package com.campus.bank_jpa_lab;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final BankService service;

    public AccountController(BankService service) { this.service = service; }

    @PostMapping("/open")
    public AccountResponse open(@RequestParam Long customerId,
                                @RequestParam AccountType type,
                                @RequestParam BigDecimal deposit) {
        return service.openAccount(customerId, type, deposit);
    }

    // GET /accounts?page=0&size=2&sort=balance  (slide 3.12)
    @GetMapping
    public Page<AccountResponse> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "balance") String sort) {
        Pageable p = PageRequest.of(page, size, Sort.by(sort).descending());
        return service.list(p);
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return service.deposit(id, amount);
    }

    @GetMapping("/rich")
    public List<AccountResponse> rich(@RequestParam BigDecimal above) {
        return service.richAccounts(above);
    }

    @GetMapping("/of-customer/{customerId}")
    public List<AccountResponse> ofCustomer(@PathVariable Long customerId) {
        return service.accountsOfCustomer(customerId);
    }

    @GetMapping("/total/{customerId}")
    public BigDecimal total(@PathVariable Long customerId) {
        return service.totalOfCustomer(customerId);
    }

        // POST /accounts/transfer?from=1&to=2&amount=500
    @PostMapping("/transfer")
    public String transfer(@RequestParam Long from, @RequestParam Long to,
                           @RequestParam BigDecimal amount) {
        return service.transfer(from, to, amount);
    }
}