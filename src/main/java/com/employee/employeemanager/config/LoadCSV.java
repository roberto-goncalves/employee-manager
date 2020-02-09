package com.employee.employeemanager.config;

import com.employee.employeemanager.employee.Employee;
import com.employee.employeemanager.employee.EmployeeController;
import com.employee.employeemanager.employee.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LoadCSV {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadCSV.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private ResourceLoader resourceLoader;


    @PostConstruct
    public void init() throws FileNotFoundException {
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader("/tmp/hr.csv"))) {
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] employee = line.split(";");
                LOGGER.info("Loading csv entry: "+ line);
                employeeRepository.save(new Employee(employee[0],employee[1],employee[2],new SimpleDateFormat("dd.MM.yyyy").parse(employee[3]),employee[4]));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
