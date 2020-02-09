package com.employee.employeemanager.employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeControllerTest {

    Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .setLenient()
            .setPrettyPrinting().create();
    String pattern = "yyyyMMdd";
    DateFormat df = new SimpleDateFormat(pattern);

    @Autowired
    private MockMvc mvc;

    @Test
    public void create() throws Exception{
        Employee transaction = new Employee("Adam", "Ashtray", "Finance", new Date(), "00001001");
        String json = gson.toJson(transaction);
        this.mvc.perform(post("/employee/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void update() throws Exception{
        Employee transaction = new Employee("Adam", "Ashtray", "Finance", new Date(), "00001002");
        String json = gson.toJson(transaction);
        this.mvc.perform(put("/employee/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void selectSpecific() throws Exception {
        Employee transaction = new Employee("Adam", "Ashtray", "Finance", new Date(), "00001003");
        String json = gson.toJson(transaction);
        this.mvc.perform(post("/employee/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andDo(print());
        this.mvc.perform(get("/employee/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andDo(print());
    }

    @Test
    public void selectAll() throws Exception {
        this.mvc.perform(get("/employee/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andDo(print());
    }

    @Test
    public void deleteSpecific()throws Exception {
        Employee transaction = new Employee("Adam", "Ashtray", "Finance", new Date(), "00001003");
        String json = gson.toJson(transaction);
        this.mvc.perform(post("/employee/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andDo(print());
        MvcResult mr = this.mvc.perform(delete("/employee/4").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isAccepted())
                .andDo(print())
                .andReturn();
        Employee employee = gson.fromJson(mr.getResponse().getContentAsString(), Employee.class);
        Assert.assertNull(employee);
    }
}