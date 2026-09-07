package com.acme.salarymanagement.dto;

public class CountryResponseDTO {

    private Long id;
    private String name;
    private String countryCode;
    private String currencyCode;

    public CountryResponseDTO() {
    }

    public CountryResponseDTO(
            Long id,
            String name,
            String countryCode,
            String currencyCode) {

        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.currencyCode = currencyCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}