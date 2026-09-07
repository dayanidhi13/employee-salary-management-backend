package com.acme.salarymanagement.dto;

import java.math.BigDecimal;

public class CountryPayrollResponseDTO {

    private String countryName;
    private String countryCode;
    private BigDecimal totalPayrollUsd;

    public CountryPayrollResponseDTO() {
    }

    public CountryPayrollResponseDTO(
            String countryName,
            String countryCode,
            BigDecimal totalPayrollUsd) {

        this.countryName = countryName;
        this.countryCode = countryCode;
        this.totalPayrollUsd = totalPayrollUsd;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public BigDecimal getTotalPayrollUsd() {
        return totalPayrollUsd;
    }

    public void setTotalPayrollUsd(BigDecimal totalPayrollUsd) {
        this.totalPayrollUsd = totalPayrollUsd;
    }
}