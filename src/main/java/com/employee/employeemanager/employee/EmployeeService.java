package com.employee.employeemanager.employee;

import com.employee.employeemanager.config.MongoConfig;
import com.employee.employeemanager.metric.Metric;
import com.employee.employeemanager.metric.MetricCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class EmployeeService {

    @Autowired
    MongoConfig mongoConfig;

    public List<Employee> selectAndGroup(String org) throws Exception{
        Query query = new Query();
        query.limit(20);
        query.addCriteria(Criteria.where("organization").regex(org,"i"));
        List<Employee> result = mongoConfig.mongoTemplate().find(query, Employee.class);
        return result;
    }
}
