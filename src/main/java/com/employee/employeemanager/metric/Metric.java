package com.employee.employeemanager.metric;

import org.springframework.data.annotation.Id;

import java.util.UUID;


public class Metric {
    @Id
    public String id;
    public String route;
    public String method;
    public long time;

    public Metric(String route, String method, long time) {
        this.id = UUID.randomUUID().toString();
        this.route = route;
        this.method = method;
        this.time = time;
    }


    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
