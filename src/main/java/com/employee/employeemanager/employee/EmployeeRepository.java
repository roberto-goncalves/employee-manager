package com.employee.employeemanager.employee;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findAll();
    List<Employee> findByOrganization(String organization);
}
