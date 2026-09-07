package com.acme.salarymanagement.controller;

import com.acme.salarymanagement.dto.*;

import com.acme.salarymanagement.service.AnalyticsService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(
            AnalyticsService analyticsService) {

        this.analyticsService =
                analyticsService;
    }

    @GetMapping("/summary")
    public ResponseEntity<AnalyticsSummaryResponseDTO>
    getSummary() {

        return ResponseEntity.ok(
                analyticsService.getSummary()
        );
    }

    @GetMapping("/payroll-by-country")
    public ResponseEntity<List<CountryPayrollResponseDTO>>
    getPayrollByCountry() {

        return ResponseEntity.ok(
                analyticsService.getPayrollByCountry()
        );
    }

    @GetMapping("/average-salary-by-department")
    public ResponseEntity<List<DepartmentAverageSalaryResponseDTO>>
    getAverageSalaryByDepartment() {

        return ResponseEntity.ok(
                analyticsService.getAverageSalaryByDepartment()
        );
    }

    @GetMapping("/top-paid-employees")
    public ResponseEntity<List<TopPaidEmployeeResponseDTO>>
    getTopPaidEmployees() {

        return ResponseEntity.ok(
                analyticsService.getTopPaidEmployees()
        );
    }

    @GetMapping("/recent-salary-changes")
    public ResponseEntity<List<RecentSalaryChangeResponseDTO>>
    getRecentSalaryChanges() {

        return ResponseEntity.ok(
                analyticsService.getRecentSalaryChanges()
        );
    }

    @GetMapping("/salary-distribution")
    public ResponseEntity<List<SalaryDistributionResponseDTO>>
    getSalaryDistribution() {

        return ResponseEntity.ok(
                analyticsService.getSalaryDistribution()
        );
    }
}