package com.campus.bank_jpa_lab;



import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    // ALWAYS BigDecimal for money — never double (rounding errors)!
    @Column(nullable = false)
    private BigDecimal balance;

    // Many accounts -> one customer. Creates a customer_id FOREIGN KEY column.
    // @ManyToOne default fetch = EAGER (the "combo meal", slide 3.10).
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Account() { }

    public Account(String accountNumber, AccountType type,
                   BigDecimal balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = balance;
        this.customer = customer;
    }

    public Long getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public AccountType getType() { return type; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

        @org.springframework.data.annotation.CreatedDate
    @Column(updatable = false)
    private java.time.LocalDateTime createdAt;      // stamped at INSERT

    @org.springframework.data.annotation.LastModifiedDate
    private java.time.LocalDateTime updatedAt;      // re-stamped at every UPDATE

    @org.springframework.data.annotation.CreatedBy
    @Column(updatable = false)
    private String createdBy;                       // who opened the account

    @org.springframework.data.annotation.LastModifiedBy
    private String lastModifiedBy;                  // who last touched it

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getLastModifiedBy() { return lastModifiedBy; }
}