package com.campus.bank_jpa_lab;

import java.math.BigDecimal;

// What WE send out — flat and safe: customer reduced to just the name.
// No panNumber, no email, no nested entity. The leak is closed.
public record AccountResponse(Long id, String accountNumber, AccountType type,
                              BigDecimal balance, String customerName) { }