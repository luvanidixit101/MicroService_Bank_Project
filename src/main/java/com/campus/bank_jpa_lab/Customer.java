package com.campus.bank_jpa_lab;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity                                   // "this class = a database table"
@Table(name = "customers")                // table name (default would be "customer")
public class Customer {

    @Id                                   // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String name;

    @Column(unique = true)                // bank rule: one email = one customer
    private String email;

    @Column(unique = true, length = 10)   // SENSITIVE — watch this field in Step 7!
    private String panNumber;

    @Enumerated(EnumType.STRING)          // stores "VERIFIED", not 0/1 — always STRING!
    private KycStatus kycStatus;

    private LocalDate joinedDate;         // maps to a DATE column automatically

    // --- constructors ---
    public Customer() { }                 // JPA REQUIRES a no-arg constructor

    public Customer(String name, String email, String panNumber,
                    KycStatus kycStatus, LocalDate joinedDate) {
        this.name = name;
        this.email = email;
        this.panNumber = panNumber;
        this.kycStatus = kycStatus;
        this.joinedDate = joinedDate;
    }

    // --- getters & setters (JPA and JSON need them) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
    public KycStatus getKycStatus() { return kycStatus; }
    public void setKycStatus(KycStatus kycStatus) { this.kycStatus = kycStatus; }
    public LocalDate getJoinedDate() { return joinedDate; }
    public void setJoinedDate(LocalDate joinedDate) { this.joinedDate = joinedDate; }
}