package com.acme.salarymanagement.config;

import com.acme.salarymanagement.entity.Country;
import com.acme.salarymanagement.entity.Department;
import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.entity.EmployeeStatus;
import com.acme.salarymanagement.entity.Salary;
import com.acme.salarymanagement.repository.CountryRepository;
import com.acme.salarymanagement.repository.DepartmentRepository;
import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.repository.SalaryRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final int TARGET_EMPLOYEE_COUNT = 10_000;

    private static final int BATCH_SIZE = 500;

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CountryRepository countryRepository;
    private final SalaryRepository salaryRepository;

    public DataSeeder(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            CountryRepository countryRepository,
            SalaryRepository salaryRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.countryRepository = countryRepository;
        this.salaryRepository = salaryRepository;
    }

    @Override
    public void run(String... args) {

        long existingEmployeeCount = employeeRepository.count();

        if (existingEmployeeCount >= TARGET_EMPLOYEE_COUNT) {

            System.out.println(
                    "Employee seed skipped. Existing employee count = "
                            + existingEmployeeCount
            );

            return;
        }

        System.out.println(
                "Starting employee seed. Existing employees = "
                        + existingEmployeeCount
        );

        List<Department> departments =
                prepareDepartments();

        List<Country> countries =
                prepareCountries();

        int employeesToCreate =
                TARGET_EMPLOYEE_COUNT
                        - (int) existingEmployeeCount;

        seedEmployees(
                employeesToCreate,
                departments,
                countries
        );

        System.out.println(
                "Employee seed completed."
        );

        System.out.println(
                "Total employees = "
                        + employeeRepository.count()
        );
    }

    private List<Department> prepareDepartments() {

        String[][] departmentData = {

                {
                        "Engineering",
                        "Software engineering and technology"
                },

                {
                        "Human Resources",
                        "Employee and workforce management"
                },

                {
                        "Finance",
                        "Finance and accounting"
                },

                {
                        "Sales",
                        "Sales and business development"
                },

                {
                        "Marketing",
                        "Marketing and communications"
                },

                {
                        "Operations",
                        "Business operations"
                },

                {
                        "Procurement",
                        "Procurement and vendor management"
                },

                {
                        "Customer Support",
                        "Customer service and support"
                }
        };

        List<Department> existingDepartments =
                departmentRepository.findAll();

        for (String[] data : departmentData) {

            boolean exists =
                    existingDepartments
                            .stream()
                            .anyMatch(department ->
                                    department
                                            .getName()
                                            .equalsIgnoreCase(
                                                    data[0]
                                            )
                            );

            if (!exists) {

                Department department =
                        new Department(
                                data[0],
                                data[1]
                        );

                departmentRepository.save(
                        department
                );

                existingDepartments.add(
                        department
                );
            }
        }

        return departmentRepository.findAll();
    }

    private List<Country> prepareCountries() {

        String[][] countryData = {

                {
                        "India",
                        "IN",
                        "INR"
                },

                {
                        "United States",
                        "US",
                        "USD"
                },

                {
                        "United Kingdom",
                        "GB",
                        "GBP"
                },

                {
                        "Germany",
                        "DE",
                        "EUR"
                },

                {
                        "Canada",
                        "CA",
                        "CAD"
                },

                {
                        "Australia",
                        "AU",
                        "AUD"
                },

                {
                        "Singapore",
                        "SG",
                        "SGD"
                },

                {
                        "Japan",
                        "JP",
                        "JPY"
                }
        };

        List<Country> existingCountries =
                countryRepository.findAll();

        for (String[] data : countryData) {

            boolean exists =
                    existingCountries
                            .stream()
                            .anyMatch(country ->
                                    country
                                            .getCountryCode()
                                            .equalsIgnoreCase(
                                                    data[1]
                                            )
                            );

            if (!exists) {

                Country country =
                        new Country();

                country.setName(
                        data[0]
                );

                country.setCountryCode(
                        data[1]
                );

                country.setCurrencyCode(
                        data[2]
                );

                Country savedCountry =
                        countryRepository.save(
                                country
                        );

                existingCountries.add(
                        savedCountry
                );
            }
        }

        return countryRepository.findAll();
    }

    private void seedEmployees(
            int employeesToCreate,
            List<Department> departments,
            List<Country> countries) {

        Random random =
                new Random(100);

        int created = 0;

        int sequence = 1;

        while (created < employeesToCreate) {

            int currentBatchSize =
                    Math.min(
                            BATCH_SIZE,
                            employeesToCreate - created
                    );

            List<Employee> employeeBatch =
                    new ArrayList<>();

            for (int i = 0;
                 i < currentBatchSize;
                 i++) {

                String employeeCode;

                String email;

                do {

                    employeeCode =
                            String.format(
                                    "SEED%05d",
                                    sequence
                            );

                    email =
                            String.format(
                                    "employee%05d@acme.com",
                                    sequence
                            );

                    sequence++;

                } while (
                        employeeRepository
                                .existsByEmployeeCodeIgnoreCase(
                                        employeeCode
                                )
                                ||
                                employeeRepository
                                        .existsByEmailIgnoreCase(
                                                email
                                        )
                );

                Employee employee =
                        new Employee();

                employee.setEmployeeCode(
                        employeeCode
                );

                employee.setFirstName(
                        generateFirstName(
                                random
                        )
                );

                employee.setLastName(
                        generateLastName(
                                random
                        )
                );

                employee.setEmail(
                        email
                );

                employee.setDesignation(
                        generateDesignation(
                                random
                        )
                );

                employee.setJoiningDate(
                        generateJoiningDate(
                                random
                        )
                );

                employee.setStatus(
                        generateStatus(
                                random
                        )
                );

                Department department =
                        departments.get(
                                random.nextInt(
                                        departments.size()
                                )
                        );

                Country country =
                        countries.get(
                                random.nextInt(
                                        countries.size()
                                )
                        );

                employee.setDepartment(
                        department
                );

                employee.setCountry(
                        country
                );

                employeeBatch.add(
                        employee
                );
            }

            List<Employee> savedEmployees =
                    employeeRepository.saveAll(
                            employeeBatch
                    );

            List<Salary> salaryBatch =
                    new ArrayList<>();

            for (Employee employee :
                    savedEmployees) {

                Salary salary =
                        createSalary(
                                employee,
                                random
                        );

                salaryBatch.add(
                        salary
                );
            }

            salaryRepository.saveAll(
                    salaryBatch
            );

            created +=
                    currentBatchSize;

            System.out.println(
                    "Seeded employees: "
                            + created
                            + " / "
                            + employeesToCreate
            );
        }
    }

    private Salary createSalary(
            Employee employee,
            Random random) {

        BigDecimal annualSalaryUsd =
                generateAnnualSalaryUsd(
                        random
                );

        String currencyCode =
                employee
                        .getCountry()
                        .getCurrencyCode();

        BigDecimal exchangeRateUsd =
                getExchangeRateUsd(
                        currencyCode
                );

        BigDecimal localAnnualSalary =
                annualSalaryUsd
                        .divide(
                                exchangeRateUsd,
                                2,
                                RoundingMode.HALF_UP
                        );

        Salary salary =
                new Salary();

        salary.setEmployee(
                employee
        );

        salary.setAnnualSalary(
                localAnnualSalary
        );

        salary.setCurrencyCode(
                currencyCode
        );

        salary.setExchangeRateUsd(
                exchangeRateUsd
        );

        salary.setAnnualSalaryUsd(
                annualSalaryUsd
        );

        salary.setEffectiveFrom(
                LocalDate.of(
                        2026,
                        1,
                        1
                )
        );

        return salary;
    }

    private BigDecimal generateAnnualSalaryUsd(
            Random random) {

        int salary =
                30_000
                        + random.nextInt(
                        170_001
                );

        return BigDecimal
                .valueOf(
                        salary
                )
                .setScale(
                        2,
                        RoundingMode.HALF_UP
                );
    }

    private BigDecimal getExchangeRateUsd(
            String currencyCode) {

        Map<String, BigDecimal> rates =
                Map.of(
                        "USD",
                        new BigDecimal("1.000000"),

                        "INR",
                        new BigDecimal("0.012000"),

                        "GBP",
                        new BigDecimal("1.270000"),

                        "EUR",
                        new BigDecimal("1.080000"),

                        "CAD",
                        new BigDecimal("0.740000"),

                        "AUD",
                        new BigDecimal("0.660000"),

                        "SGD",
                        new BigDecimal("0.740000"),

                        "JPY",
                        new BigDecimal("0.006800")
                );

        return rates.getOrDefault(
                currencyCode,
                BigDecimal.ONE
        );
    }

    private String generateFirstName(
            Random random) {

        String[] firstNames = {
                "Rahul",
                "Amit",
                "Priya",
                "Neha",
                "Arjun",
                "Ravi",
                "Anjali",
                "Vikram",
                "Sneha",
                "Karan",
                "John",
                "Emma",
                "David",
                "Sophia",
                "Michael",
                "Olivia"
        };

        return firstNames[
                random.nextInt(
                        firstNames.length
                )
                ];
    }

    private String generateLastName(
            Random random) {

        String[] lastNames = {
                "Sharma",
                "Verma",
                "Singh",
                "Patel",
                "Jain",
                "Gupta",
                "Kumar",
                "Mehta",
                "Smith",
                "Brown",
                "Johnson",
                "Wilson",
                "Taylor",
                "Martin"
        };

        return lastNames[
                random.nextInt(
                        lastNames.length
                )
                ];
    }

    private String generateDesignation(
            Random random) {

        String[] designations = {
                "Software Engineer",
                "Senior Software Engineer",
                "Technical Lead",
                "Manager",
                "Business Analyst",
                "HR Executive",
                "Finance Analyst",
                "Sales Executive",
                "Operations Manager",
                "Support Engineer",
                "Product Manager",
                "Data Analyst"
        };

        return designations[
                random.nextInt(
                        designations.length
                )
                ];
    }

    private LocalDate generateJoiningDate(
            Random random) {

        int year =
                2015
                        + random.nextInt(
                        12
                );

        int month =
                1
                        + random.nextInt(
                        12
                );

        int day =
                1
                        + random.nextInt(
                        28
                );

        return LocalDate.of(
                year,
                month,
                day
        );
    }

    private EmployeeStatus generateStatus(
            Random random) {

        // Approximately 90% active
        if (random.nextInt(10) < 9) {

            return EmployeeStatus.ACTIVE;
        }

        return EmployeeStatus.INACTIVE;
    }
}