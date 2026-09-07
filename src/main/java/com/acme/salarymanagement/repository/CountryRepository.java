package com.acme.salarymanagement.repository;

import com.acme.salarymanagement.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCountryCodeIgnoreCase(String countryCode);
}