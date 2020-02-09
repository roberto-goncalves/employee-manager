package com.employee.employeemanager.metric;

import com.employee.employeemanager.config.MongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class MetricService {

    @Autowired
    MongoConfig mongoConfig;

    public List<MetricCount> selectAndGroup() throws Exception{
        Aggregation agg = newAggregation(
                group("method", "route").count().as("total")
                        .sum("time").as("time"),
                project("_id.method","route","time","total").and("time").divide("total").as("average")
        );
        AggregationResults<MetricCount> groupResults = mongoConfig.mongoTemplate().aggregate(agg, Metric.class, MetricCount.class);
        List<MetricCount> result_method = groupResults.getMappedResults();
        return result_method;
    }
}
