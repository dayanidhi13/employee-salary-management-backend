package com.acme.salarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CountryRequestDTO {

    @NotBlank(message = "Country name is required")
    @Size(max = 100, message = "Country name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 2, message = "Country code must contain exactly 2 characters")
    private String countryCode;

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must contain exactly 3 characters")
    private String currencyCode;

    public CountryRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}