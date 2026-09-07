package com.acme.salarymanagement.service;

import com.acme.salarymanagement.dto.CountryRequestDTO;
import com.acme.salarymanagement.dto.CountryResponseDTO;
import com.acme.salarymanagement.entity.Country;
import com.acme.salarymanagement.exception.DuplicateResourceException;
import com.acme.salarymanagement.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryResponseDTO createCountry(CountryRequestDTO requestDTO) {

        String countryName = requestDTO.getName().trim();
        String countryCode = requestDTO.getCountryCode().trim().toUpperCase();
        String currencyCode = requestDTO.getCurrencyCode().trim().toUpperCase();

        if (countryRepository.existsByNameIgnoreCase(countryName)) {
            throw new DuplicateResourceException(
                    "Country name already exists"
            );
        }

        if (countryRepository.existsByCountryCodeIgnoreCase(countryCode)) {
            throw new DuplicateResourceException(
                    "Country code already exists"
            );
        }

        Country country = new Country();

        country.setName(countryName);
        country.setCountryCode(countryCode);
        country.setCurrencyCode(currencyCode);

        Country savedCountry = countryRepository.save(country);

        return mapToResponseDTO(savedCountry);
    }

    public List<CountryResponseDTO> getAllCountries() {

        return countryRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private CountryResponseDTO mapToResponseDTO(Country country) {

        return new CountryResponseDTO(
                country.getId(),
                country.getName(),
                country.getCountryCode(),
                country.getCurrencyCode()
        );
    }
}