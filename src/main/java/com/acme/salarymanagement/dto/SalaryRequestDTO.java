package com.acme.salarymanagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryRequestDTO {

    @NotNull(message = "Employee id is required")
    private Long employeeId;

    @NotNull(message = "Annual salary is required")
    @DecimalMin(
            value = "0.01",
            message = "Annual salary must be greater than zero"
    )
    private BigDecimal annualSalary;

    @NotNull(message = "Exchange rate is required")
    @DecimalMin(
            value = "0.000001",
            message = "Exchange rate must be greater than zero"
    )
    private BigDecimal exchangeRateUsd;

    @NotNull(message = "Effective date is required")
    private LocalDate effectiveFrom;

    public SalaryRequestDTO() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(BigDecimal annualSalary) {
        this.annualSalary = annualSalary;
    }

    public BigDecimal getExchangeRateUsd() {
        return exchangeRateUsd;
    }

    public void setExchangeRateUsd(BigDecimal exchangeRateUsd) {
        this.exchangeRateUsd = exchangeRateUsd;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
}