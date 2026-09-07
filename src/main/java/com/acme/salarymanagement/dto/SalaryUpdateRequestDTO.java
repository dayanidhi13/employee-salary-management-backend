package com.acme.salarymanagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryUpdateRequestDTO {

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

    @Size(max = 255, message = "Reason must not exceed 255 characters")
    private String reason;

    public SalaryUpdateRequestDTO() {
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}