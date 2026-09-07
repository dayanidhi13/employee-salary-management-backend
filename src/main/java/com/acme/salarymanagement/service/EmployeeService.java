package com.acme.salarymanagement.service;

import com.acme.salarymanagement.dto.EmployeeRequestDTO;
import com.acme.salarymanagement.dto.EmployeeResponseDTO;
import com.acme.salarymanagement.entity.Country;
import com.acme.salarymanagement.entity.Department;
import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.entity.EmployeeStatus;
import com.acme.salarymanagement.exception.DuplicateResourceException;
import com.acme.salarymanagement.exception.ResourceNotFoundException;
import com.acme.salarymanagement.repository.CountryRepository;
import com.acme.salarymanagement.repository.DepartmentRepository;
import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.specification.EmployeeSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CountryRepository countryRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            CountryRepository countryRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.countryRepository = countryRepository;
    }

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {

        String employeeCode = requestDTO.getEmployeeCode().trim();
        String email = requestDTO.getEmail().trim().toLowerCase();

        if (employeeRepository.existsByEmployeeCodeIgnoreCase(employeeCode)) {
            throw new DuplicateResourceException(
                    "Employee code already exists"
            );
        }

        if (employeeRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException(
                    "Employee email already exists"
            );
        }

        Department department = departmentRepository
                .findById(requestDTO.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: "
                                        + requestDTO.getDepartmentId()
                        )
                );

        Country country = countryRepository
                .findById(requestDTO.getCountryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Country not found with id: "
                                        + requestDTO.getCountryId()
                        )
                );

        Employee employee = new Employee();

        employee.setEmployeeCode(employeeCode);
        employee.setFirstName(requestDTO.getFirstName().trim());
        employee.setLastName(requestDTO.getLastName().trim());
        employee.setEmail(email);
        employee.setDesignation(requestDTO.getDesignation().trim());
        employee.setJoiningDate(requestDTO.getJoiningDate());
        employee.setStatus(requestDTO.getStatus());

        employee.setDepartment(department);
        employee.setCountry(country);

        Employee savedEmployee =
                employeeRepository.save(employee);

        return mapToResponseDTO(savedEmployee);
    }

    @Transactional
    public EmployeeResponseDTO updateEmployee(
            Long id,
            EmployeeRequestDTO requestDTO) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        String employeeCode =
                requestDTO.getEmployeeCode().trim();

        String email =
                requestDTO.getEmail()
                        .trim()
                        .toLowerCase();

        /*
         * Check employee code only when it is being changed.
         *
         * Example:
         *
         * Existing code = EMP001
         * Request code  = EMP001
         *
         * This is the same employee, so it is NOT a duplicate.
         */
        if (!employee.getEmployeeCode()
                .equalsIgnoreCase(employeeCode)) {

            if (employeeRepository
                    .existsByEmployeeCodeIgnoreCase(employeeCode)) {

                throw new DuplicateResourceException(
                        "Employee code already exists"
                );
            }
        }

        /*
         * Same logic for email.
         */
        if (!employee.getEmail()
                .equalsIgnoreCase(email)) {

            if (employeeRepository
                    .existsByEmailIgnoreCase(email)) {

                throw new DuplicateResourceException(
                        "Employee email already exists"
                );
            }
        }

        Department department = departmentRepository
                .findById(requestDTO.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: "
                                        + requestDTO.getDepartmentId()
                        )
                );

        Country country = countryRepository
                .findById(requestDTO.getCountryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Country not found with id: "
                                        + requestDTO.getCountryId()
                        )
                );

        employee.setEmployeeCode(employeeCode);
        employee.setFirstName(
                requestDTO.getFirstName().trim()
        );
        employee.setLastName(
                requestDTO.getLastName().trim()
        );
        employee.setEmail(email);
        employee.setDesignation(
                requestDTO.getDesignation().trim()
        );
        employee.setJoiningDate(
                requestDTO.getJoiningDate()
        );
        employee.setStatus(
                requestDTO.getStatus()
        );

        employee.setDepartment(department);
        employee.setCountry(country);

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return mapToResponseDTO(updatedEmployee);
    }

    public List<EmployeeResponseDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        return mapToResponseDTO(employee);
    }

    private EmployeeResponseDTO mapToResponseDTO(Employee employee) {

        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDesignation(),
                employee.getJoiningDate(),
                employee.getStatus(),

                employee.getDepartment().getId(),
                employee.getDepartment().getName(),

                employee.getCountry().getId(),
                employee.getCountry().getName(),
                employee.getCountry().getCountryCode(),
                employee.getCountry().getCurrencyCode(),

                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> getEmployees(
            String search,
            Long departmentId,
            Long countryId,
            EmployeeStatus status,
            int page,
            int size) {

        Specification<Employee> specification =
                EmployeeSpecification.search(search)
                        .and(EmployeeSpecification.hasDepartment(departmentId))
                        .and(EmployeeSpecification.hasCountry(countryId))
                        .and(EmployeeSpecification.hasStatus(status));

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("id").ascending()
                );

        return employeeRepository
                .findAll(specification, pageable)
                .map(this::mapToResponseDTO);
    }
}