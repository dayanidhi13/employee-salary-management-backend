package com.acme.salarymanagement.dto;

import java.math.BigDecimal;

public class DepartmentAverageSalaryResponseDTO {

    private String departmentName;
    private BigDecimal averageSalaryUsd;

    public DepartmentAverageSalaryResponseDTO() {
    }

    public DepartmentAverageSalaryResponseDTO(
            String departmentName,
            BigDecimal averageSalaryUsd) {

        this.departmentName = departmentName;
        this.averageSalaryUsd = averageSalaryUsd;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public BigDecimal getAverageSalaryUsd() {
        return averageSalaryUsd;
    }

    public void setAverageSalaryUsd(BigDecimal averageSalaryUsd) {
        this.averageSalaryUsd = averageSalaryUsd;
    }
}