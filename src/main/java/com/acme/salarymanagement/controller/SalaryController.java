package com.acme.salarymanagement.controller;

import com.acme.salarymanagement.dto.SalaryHistoryResponseDTO;
import com.acme.salarymanagement.dto.SalaryRequestDTO;
import com.acme.salarymanagement.dto.SalaryResponseDTO;
import com.acme.salarymanagement.dto.SalaryUpdateRequestDTO;

import com.acme.salarymanagement.service.SalaryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(
            SalaryService salaryService) {

        this.salaryService =
                salaryService;
    }

    // =========================================================
    // CREATE INITIAL SALARY
    // =========================================================

    @PostMapping
    public ResponseEntity<SalaryResponseDTO>
    createSalary(
            @Valid
            @RequestBody
            SalaryRequestDTO requestDTO) {

        SalaryResponseDTO response =
                salaryService
                        .createSalary(requestDTO);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // =========================================================
    // GET ALL CURRENT SALARIES
    // =========================================================

    @GetMapping
    public ResponseEntity<List<SalaryResponseDTO>>
    getAllSalaries() {

        return ResponseEntity.ok(
                salaryService
                        .getAllSalaries()
        );
    }

    // =========================================================
    // GET CURRENT SALARY BY EMPLOYEE ID
    // =========================================================

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<SalaryResponseDTO>
    getSalaryByEmployeeId(
            @PathVariable
            Long employeeId) {

        return ResponseEntity.ok(
                salaryService
                        .getSalaryByEmployeeId(
                                employeeId
                        )
        );
    }

    // =========================================================
    // UPDATE SALARY
    // =========================================================

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<SalaryResponseDTO>
    updateSalary(
            @PathVariable
            Long employeeId,

            @Valid
            @RequestBody
            SalaryUpdateRequestDTO requestDTO) {

        return ResponseEntity.ok(
                salaryService
                        .updateSalary(
                                employeeId,
                                requestDTO
                        )
        );
    }

    // =========================================================
    // GET SALARY HISTORY
    // =========================================================

    @GetMapping("/employee/{employeeId}/history")
    public ResponseEntity<List<SalaryHistoryResponseDTO>>
    getSalaryHistory(
            @PathVariable
            Long employeeId) {

        return ResponseEntity.ok(
                salaryService
                        .getSalaryHistoryByEmployeeId(
                                employeeId
                        )
        );
    }
}