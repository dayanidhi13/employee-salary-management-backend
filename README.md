# Employee Salary Management System

## 1. Project Overview

The Employee Salary Management System is a web-based HR application developed for ACME HR to manage employee and salary information across multiple countries.

The system is designed to support approximately 10,000 employees and replaces spreadsheet-based salary management with a centralized and secure application.

The backend provides REST APIs for:

- Employee management
- Department management
- Country management
- Salary management
- Salary history and auditing
- Salary analytics
- Authentication and authorization

The application follows a modular monolithic architecture using Spring Boot and PostgreSQL.

---

## 2. Technology Stack

### Backend

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT Authentication
- Maven

### Database

- PostgreSQL 15

### Validation

- Jakarta Bean Validation

### Security

- Spring Security
- JWT (JSON Web Token)
- BCrypt password hashing
- Role-based authorization

### Testing

- JUnit 5
- Mockito

### Frontend

- Angular

---

## 3. Architecture

The application follows a layered modular-monolith architecture.

```text
Angular Frontend
       |
       | REST API + JWT
       v
Spring Boot Backend
       |
       ├── Controller Layer
       |
       ├── Service Layer
       |
       ├── Repository Layer
       |
       └── JPA / Hibernate
              |
              v
          PostgreSQL
```

The backend is organized into business modules such as:

- Employee
- Department
- Country
- Salary
- Salary History
- Analytics
- Security

---

## 4. Core Functionalities

### Employee Management

HR Managers can:

- Create employees
- View employee details
- Update employee information
- Search employees
- Filter employees by department
- Filter employees by country
- Filter employees by status
- View employees using pagination

Employee code and email are unique.

---

## 5. Department Management

The application supports department master data.

HR Managers can:

- Create departments
- View departments

Department names are unique.

---

## 6. Country Management

The application maintains country and currency information.

Each country contains:

- Country name
- Country code
- Currency code

This information is used when managing employee salaries.

---

## 7. Salary Management

Each employee has one current salary record.

Salary information contains:

- Annual salary
- Local currency
- USD exchange rate
- Annual salary normalized to USD
- Effective date

`BigDecimal` is used for monetary calculations to avoid floating-point precision issues.

---

## 8. Salary Update and History

When an employee's salary is updated, the application automatically stores the previous salary information in salary history.

Salary history contains:

- Old salary
- New salary
- Currency
- Old salary in USD
- New salary in USD
- Salary change percentage
- Effective date
- Reason
- Changed by
- Changed timestamp

The salary update and salary-history creation are performed within the same database transaction.

---

## 9. Salary Analytics

The backend provides analytics APIs for the HR dashboard.

Available analytics include:

- Total employees
- Total annual payroll
- Average annual salary
- Highest annual salary
- Payroll by country
- Average salary by department
- Top paid employees
- Recent salary changes
- Salary distribution

Organization-wide salary analytics use normalized USD salary values so salaries from different countries can be compared consistently.

---

## 10. Security

The application uses Spring Security with JWT authentication.

Authentication flow:

```text
HR Manager
    |
    | username + password
    v
POST /api/auth/login
    |
    v
Spring Security Authentication
    |
    v
JWT Generated
    |
    v
Angular / Client
    |
    | Authorization: Bearer <JWT>
    v
Protected REST APIs
```

The application currently supports:

```text
ROLE_HR_MANAGER
```

All business APIs require the HR Manager role.

The authentication endpoints are publicly accessible.

---

## 11. JWT Configuration

JWT authentication is stateless.

The server does not maintain an HTTP login session.

Each protected request must contain:

```http
Authorization: Bearer <JWT_TOKEN>
```

JWT expiration:

```text
1 hour
```

Invalid or expired JWT tokens return an unauthorized response.

---

## 12. CORS

CORS is configured to allow the Angular development application:

```text
http://localhost:4200
```

Backend development URL:

```text
http://localhost:8080
```

---

# 13. REST API Endpoints

Base backend URL:

```text
http://localhost:8080
```

## Authentication APIs

### Register HR Manager

```http
POST /api/auth/register
```

### Login

```http
POST /api/auth/login
```

Login returns a JWT token.

---

## Department APIs

### Create Department

```http
POST /api/departments
```

### Get Departments

```http
GET /api/departments
```

---

## Country APIs

### Create Country

```http
POST /api/countries
```

### Get Countries

```http
GET /api/countries
```

---

## Employee APIs

### Create Employee

```http
POST /api/employees
```

### Get Employee by ID

```http
GET /api/employees/{id}
```

Example:

```http
GET /api/employees/1
```

### Update Employee

```http
PUT /api/employees/{id}
```

Example:

```http
PUT /api/employees/1
```

### Get Employees with Pagination

```http
GET /api/employees?page=0&size=10
```

### Search Employees

```http
GET /api/employees?search=rahul&page=0&size=10
```

### Filter by Department

```http
GET /api/employees?departmentId=1&page=0&size=10
```

### Filter by Country

```http
GET /api/employees?countryId=1&page=0&size=10
```

### Filter by Employee Status

```http
GET /api/employees?status=ACTIVE&page=0&size=10
```

Filters can also be combined.

Example:

```http
GET /api/employees?departmentId=1&countryId=1&status=ACTIVE&page=0&size=10
```

---

## Salary APIs

### Create Employee Salary

```http
POST /api/salaries
```

### Get All Salaries

```http
GET /api/salaries
```

### Get Employee Salary

```http
GET /api/salaries/employee/{employeeId}
```

Example:

```http
GET /api/salaries/employee/1
```

### Update Employee Salary

```http
PUT /api/salaries/employee/{employeeId}
```

Example:

```http
PUT /api/salaries/employee/1
```

### Get Employee Salary History

```http
GET /api/salaries/employee/{employeeId}/history
```

---

# 14. Analytics APIs

## Dashboard Summary

```http
GET /api/analytics/summary
```

Provides:

- Total employees
- Total annual payroll
- Average annual salary
- Highest annual salary

---

## Payroll by Country

```http
GET /api/analytics/payroll-by-country
```

---

## Average Salary by Department

```http
GET /api/analytics/average-salary-by-department
```

---

## Top Paid Employees

```http
GET /api/analytics/top-paid
```

---

## Recent Salary Changes

```http
GET /api/analytics/recent-salary-changes
```

---

## Salary Distribution

```http
GET /api/analytics/salary-distribution
```

---

# 15. Database Design

The main database tables are:

```text
departments
countries
employees
salaries
salary_history
users
```

Relationships:

```text
Department
    |
    | 1 : N
    v
Employee
    ^
    | N : 1
Country


Employee
    |
    | 1 : 1
    v
Salary


Employee
    |
    | 1 : N
    v
Salary History
```

---

# 16. Performance Considerations

The system is designed to manage approximately 10,000 employees.

Performance considerations include:

- Server-side pagination
- Dynamic employee filtering
- Database indexes
- Efficient JPQL queries
- `JOIN FETCH` for selected analytics queries
- Database-side limiting using `Pageable`
- SQL console logging disabled for normal execution

Indexes are used for frequently queried fields including:

- Employee department
- Employee country
- Employee status
- Annual salary in USD
- Salary-history employee
- Salary-history changed timestamp

---

# 17. Seed Data

The application supports approximately 10,000 employee records for assessment and performance testing.

Seed data includes:

- Employees
- Departments
- Countries
- Salary records

The seeder checks existing employee data before generating additional records to avoid recreating all 10,000 employees on every application restart.

---

# 18. Environment Variables

Sensitive configuration is not stored directly in source code.

The following environment variables are required:

```text
DB_PASSWORD
JWT_SECRET
```

Optional environment variables:

```text
DB_URL
DB_USERNAME
```

Example `application.properties` configuration:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/employee_salary_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000
```

Do not commit actual passwords or JWT secrets to the repository.

---

# 19. Local Setup

## Prerequisites

Install:

- Java 17
- Maven
- PostgreSQL 15

Create PostgreSQL database:

```text
employee_salary_db
```

Configure environment variables:

```text
DB_PASSWORD
JWT_SECRET
```

Run the application:

```bash
mvn spring-boot:run
```

The backend will start at:

```text
http://localhost:8080
```

---

# 20. API Authentication

First login using:

```http
POST /api/auth/login
```

Copy the generated JWT.

For protected APIs, send:

```http
Authorization: Bearer <JWT_TOKEN>
```

Example:

```http
GET /api/employees?page=0&size=10
Authorization: Bearer <JWT_TOKEN>
```

---

# 21. Validation and Error Handling

The application provides validation and centralized exception handling for cases including:

- Invalid request data
- Duplicate employee code
- Duplicate employee email
- Duplicate department
- Duplicate country
- Resource not found
- Invalid JWT
- Expired JWT
- Unauthorized access

Typical HTTP status codes include:

```text
200 OK
201 Created
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
```

---

# 22. Deliberate Scope Decisions

The application focuses on employee salary management and HR analytics.

The following features are intentionally outside the current scope:

- Payroll processing
- Tax calculation
- Salary deductions
- Payslip generation
- Bonus processing
- Real-time foreign exchange integration
- Enterprise IAM
- LDAP
- Keycloak
- Microservices
- Kafka
- Kubernetes

A modular monolithic architecture was selected because it provides clear separation of business functionality without introducing unnecessary distributed-system complexity for the current requirements.

---

# 23. Future Improvements

Potential future improvements include:

- Flyway database migrations
- Externalized production configuration
- Real-time foreign exchange integration
- Additional HR roles and permissions
- Advanced reporting and export
- Containerized deployment
- Expanded automated test coverage