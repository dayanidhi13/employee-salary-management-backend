package com.acme.salarymanagement.dto;

import java.math.BigDecimal;

public class AnalyticsSummaryResponseDTO {

    private long totalEmployees;
    private BigDecimal totalAnnualPayroll;
    private BigDecimal averageAnnualSalary;
    private BigDecimal highestAnnualSalary;

    public AnalyticsSummaryResponseDTO() {
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public BigDecimal getTotalAnnualPayroll() {
        return totalAnnualPayroll;
    }

    public void setTotalAnnualPayroll(BigDecimal totalAnnualPayroll) {
        this.totalAnnualPayroll = totalAnnualPayroll;
    }

    public BigDecimal getAverageAnnualSalary() {
        return averageAnnualSalary;
    }

    public void setAverageAnnualSalary(BigDecimal averageAnnualSalary) {
        this.averageAnnualSalary = averageAnnualSalary;
    }

    public BigDecimal getHighestAnnualSalary() {
        return highestAnnualSalary;
    }

    public void setHighestAnnualSalary(BigDecimal highestAnnualSalary) {
        this.highestAnnualSalary = highestAnnualSalary;
    }
}