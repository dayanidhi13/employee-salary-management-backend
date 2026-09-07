package com.acme.salarymanagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "salary_history",
        indexes = {
                @Index(
                        name = "idx_salary_history_employee",
                        columnList = "employee_id"
                ),
                @Index(
                        name = "idx_salary_history_changed_at",
                        columnList = "changed_at"
                )
        }
)
public class SalaryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "employee_id",
            nullable = false
    )
    private Employee employee;

    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal oldSalary;

    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal newSalary;

    @Column(
            nullable = false,
            length = 10
    )
    private String currencyCode;

    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal oldSalaryUsd;

    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal newSalaryUsd;

    @Column(
            nullable = false,
            precision = 7,
            scale = 2
    )
    private BigDecimal changePercentage;

    @Column(nullable = false)
    private LocalDate effectiveDate;

    @Column(length = 255)
    private String reason;

    @Column(
            name = "changed_by",
            nullable = false,
            length = 100
    )
    private String changedBy;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    public SalaryHistory() {
    }

    @PrePersist
    public void prePersist() {
        changedAt = LocalDateTime.now();
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

    public BigDecimal getOldSalary() {
        return oldSalary;
    }

    public void setOldSalary(BigDecimal oldSalary) {
        this.oldSalary = oldSalary;
    }

    public BigDecimal getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(BigDecimal newSalary) {
        this.newSalary = newSalary;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getOldSalaryUsd() {
        return oldSalaryUsd;
    }

    public void setOldSalaryUsd(BigDecimal oldSalaryUsd) {
        this.oldSalaryUsd = oldSalaryUsd;
    }

    public BigDecimal getNewSalaryUsd() {
        return newSalaryUsd;
    }

    public void setNewSalaryUsd(BigDecimal newSalaryUsd) {
        this.newSalaryUsd = newSalaryUsd;
    }

    public BigDecimal getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(BigDecimal changePercentage) {
        this.changePercentage = changePercentage;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}