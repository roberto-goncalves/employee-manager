package com.employee.employeemanager.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Employee {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String organization;
    @JsonProperty("birthDate")
    @JsonFormat(pattern="yyy-MM-dd")
    private Date birthDate;
    private String costcenter;

    public Employee(String firstname, String lastname, String organization, Date birthDate, String costcenter) {
        this.id = UUID.randomUUID().toString();
        this.firstname = firstname;
        this.lastname = lastname;
        this.organization = organization;
        this.birthDate = birthDate;
        this.costcenter = costcenter;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }

}
