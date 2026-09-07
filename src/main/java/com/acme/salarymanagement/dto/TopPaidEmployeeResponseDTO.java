package com.acme.salarymanagement.dto;

import java.math.BigDecimal;

public class TopPaidEmployeeResponseDTO {

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private String departmentName;

    private String countryName;

    private BigDecimal annualSalaryUsd;

    public TopPaidEmployeeResponseDTO() {
    }

    public TopPaidEmployeeResponseDTO(
            Long employeeId,
            String employeeCode,
            String employeeName,
            String departmentName,
            String countryName,
            BigDecimal annualSalaryUsd) {

        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.departmentName = departmentName;
        this.countryName = countryName;
        this.annualSalaryUsd = annualSalaryUsd;
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

    public BigDecimal getAnnualSalaryUsd() {
        return annualSalaryUsd;
    }

    public void setAnnualSalaryUsd(BigDecimal annualSalaryUsd) {
        this.annualSalaryUsd = annualSalaryUsd;
    }
}