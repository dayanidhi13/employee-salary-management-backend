package com.acme.salarymanagement.dto;

public class SalaryDistributionResponseDTO {

    private String range;
    private long employeeCount;

    public SalaryDistributionResponseDTO() {
    }

    public SalaryDistributionResponseDTO(
            String range,
            long employeeCount) {

        this.range = range;
        this.employeeCount = employeeCount;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }
}