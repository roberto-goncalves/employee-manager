package com.employee.employeemanager.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="/employeemetrics", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MetricController {

    @Autowired
    MetricRepository metricRepository;

    @Autowired
    MetricService metricService;


    @GetMapping(path="/",consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Metric> selectAll() {
        return metricRepository.findAll();
    }

    @GetMapping(path="/stats",consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<MetricCount> selectStats() throws Exception {
        return metricService.selectAndGroup();
    }
}
