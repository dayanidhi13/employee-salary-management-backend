package com.acme.salarymanagement.dto;

import com.acme.salarymanagement.entity.EmployeeStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeResponseDTO {

    private Long id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String designation;
    private LocalDate joiningDate;
    private EmployeeStatus status;

    private Long departmentId;
    private String departmentName;

    private Long countryId;
    private String countryName;
    private String countryCode;
    private String currencyCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(
            Long id,
            String employeeCode,
            String firstName,
            String lastName,
            String email,
            String designation,
            LocalDate joiningDate,
            EmployeeStatus status,
            Long departmentId,
            String departmentName,
            Long countryId,
            String countryName,
            String countryCode,
            String currencyCode,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.employeeCode = employeeCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.status = status;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.currencyCode = currencyCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDesignation() {
        return designation;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Long getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}