package com.acme.salarymanagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "salaries",
        indexes = {
                @Index(
                        name = "idx_salary_annual_usd",
                        columnList = "annual_salary_usd"
                )
        }
)
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "employee_id",
            nullable = false,
            unique = true
    )
    private Employee employee;

    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal annualSalary;

    @Column(
            nullable = false,
            length = 10
    )
    private String currencyCode;

    @Column(
            nullable = false,
            precision = 15,
            scale = 6
    )
    private BigDecimal exchangeRateUsd;

    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal annualSalaryUsd;

    @Column(nullable = false)
    private LocalDate effectiveFrom;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Salary() {
    }

    @PrePersist
    public void prePersist() {
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(BigDecimal annualSalary) {
        this.annualSalary = annualSalary;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getExchangeRateUsd() {
        return exchangeRateUsd;
    }

    public void setExchangeRateUsd(BigDecimal exchangeRateUsd) {
        this.exchangeRateUsd = exchangeRateUsd;
    }

    public BigDecimal getAnnualSalaryUsd() {
        return annualSalaryUsd;
    }

    public void setAnnualSalaryUsd(BigDecimal annualSalaryUsd) {
        this.annualSalaryUsd = annualSalaryUsd;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}