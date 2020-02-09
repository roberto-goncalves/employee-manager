package com.employee.employeemanager.employee;

import com.employee.employeemanager.filters.MetricFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path="/employee", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @PutMapping(path="/",consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @GetMapping(path="/{id}",consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Employee> selectSpecific(@PathVariable String id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(path="/",consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Employee> selectAll(@RequestParam(required = false) String org) {
        if(org == null){
            return employeeRepository.findAll();
        }else{
            return employeeRepository.findByOrganization(org);
        }
    }

    @DeleteMapping(path="/{id}",consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteSpecific(@PathVariable String id) {
        employeeRepository.deleteById(id);
    }

    @DeleteMapping(path="/",consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAll() {
        employeeRepository.deleteAll();
    }

}