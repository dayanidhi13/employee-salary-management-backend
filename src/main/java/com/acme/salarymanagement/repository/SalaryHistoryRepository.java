package com.acme.salarymanagement.repository;

import com.acme.salarymanagement.entity.SalaryHistory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryHistoryRepository
        extends JpaRepository<SalaryHistory, Long> {


    // Salary history of one employee
    List<SalaryHistory>
    findByEmployeeIdOrderByChangedAtDesc(Long employeeId);


    // Recent salary changes
    // Pageable ensures DB returns only required rows
    @Query("""
           SELECT sh
           FROM SalaryHistory sh
           JOIN FETCH sh.employee e
           JOIN FETCH e.department
           JOIN FETCH e.country
           ORDER BY sh.changedAt DESC
           """)
    List<SalaryHistory> findRecentChanges(Pageable pageable);
}