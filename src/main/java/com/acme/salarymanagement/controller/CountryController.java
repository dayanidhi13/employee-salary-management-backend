package com.acme.salarymanagement.controller;

import com.acme.salarymanagement.dto.CountryRequestDTO;
import com.acme.salarymanagement.dto.CountryResponseDTO;
import com.acme.salarymanagement.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<CountryResponseDTO> createCountry(
            @Valid @RequestBody CountryRequestDTO requestDTO) {

        CountryResponseDTO response =
                countryService.createCountry(requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryResponseDTO>> getAllCountries() {

        return ResponseEntity.ok(
                countryService.getAllCountries()
        );
    }
}