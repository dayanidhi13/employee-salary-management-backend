package com.acme.salarymanagement.service;

import com.acme.salarymanagement.dto.AnalyticsSummaryResponseDTO;
import com.acme.salarymanagement.dto.CountryPayrollResponseDTO;
import com.acme.salarymanagement.dto.DepartmentAverageSalaryResponseDTO;
import com.acme.salarymanagement.dto.RecentSalaryChangeResponseDTO;
import com.acme.salarymanagement.dto.SalaryDistributionResponseDTO;
import com.acme.salarymanagement.dto.TopPaidEmployeeResponseDTO;

import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.repository.SalaryHistoryRepository;
import com.acme.salarymanagement.repository.SalaryRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class AnalyticsService {

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;
    private final SalaryHistoryRepository salaryHistoryRepository;


    public AnalyticsService(
            EmployeeRepository employeeRepository,
            SalaryRepository salaryRepository,
            SalaryHistoryRepository salaryHistoryRepository) {

        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
        this.salaryHistoryRepository = salaryHistoryRepository;
    }


    // =========================
    // Analytics Summary
    // =========================

    @Transactional(readOnly = true)
    public AnalyticsSummaryResponseDTO getSummary() {

        long totalEmployees =
                employeeRepository.count();

        BigDecimal totalPayroll =
                salaryRepository.getTotalAnnualPayroll();

        Double averageSalary =
                salaryRepository.getAverageAnnualSalary();

        BigDecimal highestSalary =
                salaryRepository.getHighestAnnualSalary();


        AnalyticsSummaryResponseDTO response =
                new AnalyticsSummaryResponseDTO();


        response.setTotalEmployees(
                totalEmployees
        );


        response.setTotalAnnualPayroll(
                totalPayroll != null
                        ? totalPayroll
                        : BigDecimal.ZERO
        );


        response.setAverageAnnualSalary(
                averageSalary != null
                        ? BigDecimal
                        .valueOf(averageSalary)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        )
                        : BigDecimal.ZERO
        );


        response.setHighestAnnualSalary(
                highestSalary != null
                        ? highestSalary
                        : BigDecimal.ZERO
        );


        return response;
    }


    // =========================
    // Payroll By Country
    // =========================

    @Transactional(readOnly = true)
    public List<CountryPayrollResponseDTO>
    getPayrollByCountry() {

        return salaryRepository
                .getPayrollByCountry()
                .stream()
                .map(row ->
                        new CountryPayrollResponseDTO(
                                (String) row[0],
                                (String) row[1],
                                (BigDecimal) row[2]
                        )
                )
                .toList();
    }


    // =========================
    // Average Salary By Department
    // =========================

    @Transactional(readOnly = true)
    public List<DepartmentAverageSalaryResponseDTO>
    getAverageSalaryByDepartment() {

        return salaryRepository
                .getAverageSalaryByDepartment()
                .stream()
                .map(row -> {

                    String departmentName =
                            (String) row[0];

                    Double average =
                            (Double) row[1];


                    BigDecimal averageSalary =
                            BigDecimal
                                    .valueOf(average)
                                    .setScale(
                                            2,
                                            RoundingMode.HALF_UP
                                    );


                    return new DepartmentAverageSalaryResponseDTO(
                            departmentName,
                            averageSalary
                    );
                })
                .toList();
    }


    // =========================
    // Top Paid Employees
    // Only 10 rows from DB
    // =========================

    @Transactional(readOnly = true)
    public List<TopPaidEmployeeResponseDTO>
    getTopPaidEmployees() {

        return salaryRepository
                .findTopPaidEmployees(
                        PageRequest.of(0, 10)
                )
                .stream()
                .map(salary -> {

                    String employeeName =
                            salary.getEmployee().getFirstName()
                                    + " "
                                    + salary.getEmployee().getLastName();


                    return new TopPaidEmployeeResponseDTO(

                            salary.getEmployee().getId(),

                            salary.getEmployee()
                                    .getEmployeeCode(),

                            employeeName,

                            salary.getEmployee()
                                    .getDepartment()
                                    .getName(),

                            salary.getEmployee()
                                    .getCountry()
                                    .getName(),

                            salary.getAnnualSalaryUsd()
                    );
                })
                .toList();
    }


    // =========================
    // Recent Salary Changes
    // Only 10 rows from DB
    // =========================

    @Transactional(readOnly = true)
    public List<RecentSalaryChangeResponseDTO>
    getRecentSalaryChanges() {

        return salaryHistoryRepository
                .findRecentChanges(
                        PageRequest.of(0, 10)
                )
                .stream()
                .map(history -> {

                    String employeeName =
                            history.getEmployee().getFirstName()
                                    + " "
                                    + history.getEmployee().getLastName();


                    return new RecentSalaryChangeResponseDTO(

                            history.getEmployee().getId(),

                            history.getEmployee()
                                    .getEmployeeCode(),

                            employeeName,

                            history.getEmployee()
                                    .getDepartment()
                                    .getName(),

                            history.getEmployee()
                                    .getCountry()
                                    .getName(),

                            history.getOldSalary(),

                            history.getNewSalary(),

                            history.getChangePercentage(),

                            history.getEffectiveDate(),

                            history.getChangedBy(),

                            history.getChangedAt()
                    );
                })
                .toList();
    }


    // =========================
    // Salary Distribution
    // =========================

    @Transactional(readOnly = true)
    public List<SalaryDistributionResponseDTO>
    getSalaryDistribution() {

        return List.of(

                new SalaryDistributionResponseDTO(
                        "0-50000",
                        salaryRepository
                                .countSalaryBelow50000()
                ),

                new SalaryDistributionResponseDTO(
                        "50000-100000",
                        salaryRepository
                                .countSalaryBetween50000And100000()
                ),

                new SalaryDistributionResponseDTO(
                        "100000-150000",
                        salaryRepository
                                .countSalaryBetween100000And150000()
                ),

                new SalaryDistributionResponseDTO(
                        "150000+",
                        salaryRepository
                                .countSalaryAbove150000()
                )
        );
    }
}