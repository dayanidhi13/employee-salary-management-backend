package com.acme.salarymanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RecentSalaryChangeResponseDTO {

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private String departmentName;

    private String countryName;

    private BigDecimal oldSalary;

    private BigDecimal newSalary;

    private BigDecimal changePercentage;

    private LocalDate effectiveDate;

    private String changedBy;

    private LocalDateTime changedAt;

    public RecentSalaryChangeResponseDTO() {
    }

    public RecentSalaryChangeResponseDTO(
            Long employeeId,
            String employeeCode,
            String employeeName,
            String departmentName,
            String countryName,
            BigDecimal oldSalary,
            BigDecimal newSalary,
            BigDecimal changePercentage,
            LocalDate effectiveDate,
            String changedBy,
            LocalDateTime changedAt) {

        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.departmentName = departmentName;
        this.countryName = countryName;
        this.oldSalary = oldSalary;
        this.newSalary = newSalary;
        this.changePercentage = changePercentage;
        this.effectiveDate = effectiveDate;
        this.changedBy = changedBy;
        this.changedAt = changedAt;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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