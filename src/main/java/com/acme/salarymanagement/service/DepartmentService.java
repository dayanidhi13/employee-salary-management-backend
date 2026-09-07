package com.acme.salarymanagement.service;

import com.acme.salarymanagement.dto.DepartmentRequestDTO;
import com.acme.salarymanagement.dto.DepartmentResponseDTO;
import com.acme.salarymanagement.entity.Department;
import com.acme.salarymanagement.exception.DuplicateResourceException;
import com.acme.salarymanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO) {

        if (departmentRepository.existsByNameIgnoreCase(requestDTO.getName())) {
            throw new DuplicateResourceException("Department already exists");
        }

        Department department = new Department();
        department.setName(requestDTO.getName());
        department.setDescription(requestDTO.getDescription());

        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentResponseDTO(
                savedDepartment.getId(),
                savedDepartment.getName(),
                savedDepartment.getDescription()
        );
    }

    public List<DepartmentResponseDTO> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(department -> new DepartmentResponseDTO(
                        department.getId(),
                        department.getName(),
                        department.getDescription()
                ))
                .toList();
    }
}