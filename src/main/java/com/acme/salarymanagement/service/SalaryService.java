package com.acme.salarymanagement.service;

import com.acme.salarymanagement.dto.SalaryHistoryResponseDTO;
import com.acme.salarymanagement.dto.SalaryRequestDTO;
import com.acme.salarymanagement.dto.SalaryResponseDTO;
import com.acme.salarymanagement.dto.SalaryUpdateRequestDTO;

import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.entity.Salary;
import com.acme.salarymanagement.entity.SalaryHistory;

import com.acme.salarymanagement.exception.DuplicateResourceException;
import com.acme.salarymanagement.exception.ResourceNotFoundException;

import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.repository.SalaryHistoryRepository;
import com.acme.salarymanagement.repository.SalaryRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final SalaryHistoryRepository salaryHistoryRepository;

    public SalaryService(
            SalaryRepository salaryRepository,
            EmployeeRepository employeeRepository,
            SalaryHistoryRepository salaryHistoryRepository) {

        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
        this.salaryHistoryRepository = salaryHistoryRepository;
    }

    @Transactional
    public SalaryResponseDTO createSalary(
            SalaryRequestDTO request) {

        Employee employee = employeeRepository
                .findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: "
                                        + request.getEmployeeId()
                        )
                );

        if (salaryRepository.existsByEmployeeId(
                request.getEmployeeId())) {

            throw new DuplicateResourceException(
                    "Salary already exists for employee id: "
                            + request.getEmployeeId()
            );
        }

        BigDecimal annualSalaryUsd =
                request.getAnnualSalary()
                        .multiply(request.getExchangeRateUsd())
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        Salary salary = new Salary();

        salary.setEmployee(employee);

        salary.setAnnualSalary(
                request.getAnnualSalary()
        );

        salary.setCurrencyCode(
                employee.getCountry().getCurrencyCode()
        );

        salary.setExchangeRateUsd(
                request.getExchangeRateUsd()
        );

        salary.setAnnualSalaryUsd(
                annualSalaryUsd
        );

        salary.setEffectiveFrom(
                request.getEffectiveFrom()
        );

        Salary savedSalary =
                salaryRepository.save(salary);

        return mapToResponse(savedSalary);
    }

    @Transactional(readOnly = true)
    public List<SalaryResponseDTO> getAllSalaries() {

        return salaryRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SalaryResponseDTO getSalaryByEmployeeId(
            Long employeeId) {

        Salary salary = salaryRepository
                .findByEmployeeId(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Salary not found for employee id: "
                                        + employeeId
                        )
                );

        return mapToResponse(salary);
    }

    @Transactional
    public SalaryResponseDTO updateSalary(
            Long employeeId,
            SalaryUpdateRequestDTO request) {

        Salary salary = salaryRepository
                .findByEmployeeId(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Salary not found for employee id: "
                                        + employeeId
                        )
                );

        BigDecimal oldSalary =
                salary.getAnnualSalary();

        BigDecimal oldSalaryUsd =
                salary.getAnnualSalaryUsd();

        BigDecimal newSalary =
                request.getAnnualSalary();

        BigDecimal newSalaryUsd =
                newSalary
                        .multiply(
                                request.getExchangeRateUsd()
                        )
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        BigDecimal difference =
                newSalary.subtract(oldSalary);

        BigDecimal changePercentage =
                difference
                        .divide(
                                oldSalary,
                                6,
                                RoundingMode.HALF_UP
                        )
                        .multiply(
                                BigDecimal.valueOf(100)
                        )
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String changedBy =
                authentication.getName();

        SalaryHistory history =
                new SalaryHistory();

        history.setEmployee(
                salary.getEmployee()
        );

        history.setOldSalary(
                oldSalary
        );

        history.setNewSalary(
                newSalary
        );

        history.setCurrencyCode(
                salary.getCurrencyCode()
        );

        history.setOldSalaryUsd(
                oldSalaryUsd
        );

        history.setNewSalaryUsd(
                newSalaryUsd
        );

        history.setChangePercentage(
                changePercentage
        );

        history.setEffectiveDate(
                request.getEffectiveFrom()
        );

        history.setReason(
                request.getReason()
        );

        history.setChangedBy(
                changedBy
        );

        salaryHistoryRepository.save(history);

        salary.setAnnualSalary(
                newSalary
        );

        salary.setExchangeRateUsd(
                request.getExchangeRateUsd()
        );

        salary.setAnnualSalaryUsd(
                newSalaryUsd
        );

        salary.setEffectiveFrom(
                request.getEffectiveFrom()
        );

        Salary updatedSalary =
                salaryRepository.save(salary);

        return mapToResponse(updatedSalary);
    }

    @Transactional(readOnly = true)
    public List<SalaryHistoryResponseDTO>
    getSalaryHistoryByEmployeeId(
            Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {

            throw new ResourceNotFoundException(
                    "Employee not found with id: "
                            + employeeId
            );
        }

        return salaryHistoryRepository
                .findByEmployeeIdOrderByChangedAtDesc(
                        employeeId
                )
                .stream()
                .map(this::mapHistoryToResponse)
                .toList();
    }

    private SalaryResponseDTO mapToResponse(
            Salary salary) {

        SalaryResponseDTO response =
                new SalaryResponseDTO();

        response.setId(
                salary.getId()
        );

        response.setEmployeeId(
                salary.getEmployee().getId()
        );

        response.setEmployeeCode(
                salary.getEmployee()
                        .getEmployeeCode()
        );

        response.setEmployeeName(
                salary.getEmployee().getFirstName()
                        + " "
                        + salary.getEmployee().getLastName()
        );

        response.setAnnualSalary(
                salary.getAnnualSalary()
        );

        response.setCurrencyCode(
                salary.getCurrencyCode()
        );

        response.setExchangeRateUsd(
                salary.getExchangeRateUsd()
        );

        response.setAnnualSalaryUsd(
                salary.getAnnualSalaryUsd()
        );

        response.setEffectiveFrom(
                salary.getEffectiveFrom()
        );

        response.setUpdatedAt(
                salary.getUpdatedAt()
        );

        return response;
    }

    private SalaryHistoryResponseDTO
    mapHistoryToResponse(
            SalaryHistory history) {

        SalaryHistoryResponseDTO response =
                new SalaryHistoryResponseDTO();

        response.setId(
                history.getId()
        );

        response.setEmployeeId(
                history.getEmployee().getId()
        );

        response.setEmployeeCode(
                history.getEmployee()
                        .getEmployeeCode()
        );

        response.setEmployeeName(
                history.getEmployee().getFirstName()
                        + " "
                        + history.getEmployee().getLastName()
        );

        response.setOldSalary(
                history.getOldSalary()
        );

        response.setNewSalary(
                history.getNewSalary()
        );

        response.setCurrencyCode(
                history.getCurrencyCode()
        );

        response.setOldSalaryUsd(
                history.getOldSalaryUsd()
        );

        response.setNewSalaryUsd(
                history.getNewSalaryUsd()
        );

        response.setChangePercentage(
                history.getChangePercentage()
        );

        response.setEffectiveDate(
                history.getEffectiveDate()
        );

        response.setReason(
                history.getReason()
        );

        response.setChangedBy(
                history.getChangedBy()
        );

        response.setChangedAt(
                history.getChangedAt()
        );

        return response;
    }
}