package com.acme.salarymanagement.repository;

import com.acme.salarymanagement.entity.Salary;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    boolean existsByEmployeeId(Long employeeId);

    Optional<Salary> findByEmployeeId(Long employeeId);


    // =========================
    // Analytics Summary
    // =========================

    @Query("SELECT SUM(s.annualSalaryUsd) FROM Salary s")
    BigDecimal getTotalAnnualPayroll();


    @Query("SELECT AVG(s.annualSalaryUsd) FROM Salary s")
    Double getAverageAnnualSalary();


    @Query("SELECT MAX(s.annualSalaryUsd) FROM Salary s")
    BigDecimal getHighestAnnualSalary();


    // =========================
    // Payroll By Country
    // =========================

    @Query("""
           SELECT s.employee.country.name,
                  s.employee.country.countryCode,
                  SUM(s.annualSalaryUsd)
           FROM Salary s
           GROUP BY
                  s.employee.country.name,
                  s.employee.country.countryCode
           ORDER BY SUM(s.annualSalaryUsd) DESC
           """)
    List<Object[]> getPayrollByCountry();


    // =========================
    // Average Salary By Department
    // =========================

    @Query("""
           SELECT s.employee.department.name,
                  AVG(s.annualSalaryUsd)
           FROM Salary s
           GROUP BY s.employee.department.name
           ORDER BY AVG(s.annualSalaryUsd) DESC
           """)
    List<Object[]> getAverageSalaryByDepartment();


    // =========================
    // Top Paid Employees
    // DB will return only requested rows
    // =========================

    @Query("""
           SELECT s
           FROM Salary s
           JOIN FETCH s.employee e
           JOIN FETCH e.department
           JOIN FETCH e.country
           ORDER BY s.annualSalaryUsd DESC
           """)
    List<Salary> findTopPaidEmployees(Pageable pageable);


    // =========================
    // Salary Distribution
    // =========================

    @Query("""
           SELECT COUNT(s)
           FROM Salary s
           WHERE s.annualSalaryUsd < 50000
           """)
    long countSalaryBelow50000();


    @Query("""
           SELECT COUNT(s)
           FROM Salary s
           WHERE s.annualSalaryUsd >= 50000
           AND s.annualSalaryUsd < 100000
           """)
    long countSalaryBetween50000And100000();


    @Query("""
           SELECT COUNT(s)
           FROM Salary s
           WHERE s.annualSalaryUsd >= 100000
           AND s.annualSalaryUsd < 150000
           """)
    long countSalaryBetween100000And150000();


    @Query("""
           SELECT COUNT(s)
           FROM Salary s
           WHERE s.annualSalaryUsd >= 150000
           """)
    long countSalaryAbove150000();
}