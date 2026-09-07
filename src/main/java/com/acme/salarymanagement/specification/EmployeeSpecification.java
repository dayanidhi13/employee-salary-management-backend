package com.acme.salarymanagement.specification;

import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.entity.EmployeeStatus;

import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> search(String search) {

        return (root, query, criteriaBuilder) -> {

            if (search == null || search.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String value =
                    "%" + search.trim().toLowerCase() + "%";

            return criteriaBuilder.or(

                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("employeeCode")
                            ),
                            value
                    ),

                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("firstName")
                            ),
                            value
                    ),

                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("lastName")
                            ),
                            value
                    ),

                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("email")
                            ),
                            value
                    )
            );
        };
    }

    public static Specification<Employee>
    hasDepartment(Long departmentId) {

        return (root, query, criteriaBuilder) -> {

            if (departmentId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("department").get("id"),
                    departmentId
            );
        };
    }

    public static Specification<Employee>
    hasCountry(Long countryId) {

        return (root, query, criteriaBuilder) -> {

            if (countryId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("country").get("id"),
                    countryId
            );
        };
    }

    public static Specification<Employee>
    hasStatus(EmployeeStatus status) {

        return (root, query, criteriaBuilder) -> {

            if (status == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("status"),
                    status
            );
        };
    }
}