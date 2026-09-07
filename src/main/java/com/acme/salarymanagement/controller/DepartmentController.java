package com.acme.salarymanagement.controller;

import com.acme.salarymanagement.dto.DepartmentRequestDTO;
import com.acme.salarymanagement.dto.DepartmentResponseDTO;
import com.acme.salarymanagement.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentResponseDTO createDepartment(
            @Valid @RequestBody DepartmentRequestDTO requestDTO) {

        return departmentService.createDepartment(requestDTO);
    }

    @GetMapping
    public List<DepartmentResponseDTO> getAllDepartments() {

        return departmentService.getAllDepartments();
    }
}