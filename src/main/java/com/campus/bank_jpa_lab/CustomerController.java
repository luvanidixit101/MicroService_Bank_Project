package com.campus.bank_jpa_lab;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) { this.service = service; }

    @PostMapping
    public Customer create(@RequestBody Customer c) { return service.register(c); }

    @GetMapping
    public List<Customer> all() { return service.all(); }

    @GetMapping("/{id}")
    public Customer one(@PathVariable Long id) { return service.get(id); }

    @PutMapping("/{id}/verify-kyc")
    public Customer verifyKyc(@PathVariable Long id) { return service.verifyKyc(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.remove(id); }
}