package com.campus.bank_jpa_lab;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) { this.repo = repo; }

    public Customer register(Customer c) { return repo.save(c); }         // CREATE
    public List<Customer> all()          { return repo.findAll(); }       // READ all
    public Customer get(Long id) {                                         // READ one
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }
    public Customer verifyKyc(Long id) {                                   // UPDATE
        Customer c = get(id);
        c.setKycStatus(KycStatus.VERIFIED);
        return repo.save(c);
    }
    public void remove(Long id)          { repo.deleteById(id); }         // DELETE
}