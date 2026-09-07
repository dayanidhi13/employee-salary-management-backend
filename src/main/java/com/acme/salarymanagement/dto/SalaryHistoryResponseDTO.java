package com.acme.salarymanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SalaryHistoryResponseDTO {

    private Long id;

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private BigDecimal oldSalary;

    private BigDecimal newSalary;

    private String currencyCode;

    private BigDecimal oldSalaryUsd;

    private BigDecimal newSalaryUsd;

    private BigDecimal changePercentage;

    private LocalDate effectiveDate;

    private String reason;

    private String changedBy;

    private LocalDateTime changedAt;

    public SalaryHistoryResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}