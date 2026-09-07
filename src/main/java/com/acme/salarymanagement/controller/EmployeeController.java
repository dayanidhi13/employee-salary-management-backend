package com.acme.salarymanagement.controller;

import com.acme.salarymanagement.dto.EmployeeRequestDTO;
import com.acme.salarymanagement.dto.EmployeeResponseDTO;
import com.acme.salarymanagement.entity.EmployeeStatus;
import com.acme.salarymanagement.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, requestDTO)
        );
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDTO>> getEmployees(

            @RequestParam(required = false)
            String search,

            @RequestParam(required = false)
            Long departmentId,

            @RequestParam(required = false)
            Long countryId,

            @RequestParam(required = false)
            EmployeeStatus status,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                employeeService.getEmployees(
                        search,
                        departmentId,
                        countryId,
                        status,
                        page,
                        size
                )
        );
    }
}