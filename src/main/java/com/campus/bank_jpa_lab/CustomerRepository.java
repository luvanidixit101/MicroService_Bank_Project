package com.campus.bank_jpa_lab;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<EntityType, PrimaryKeyType>
// Spring GENERATES the implementation at runtime — you write nothing!
// Free methods: save, findById, findAll, deleteById, count, existsById ...
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}