package com.employee.employeemanager.filters;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.employee.employeemanager.metric.MetricRepository;
import com.employee.employeemanager.metric.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@WebFilter("/*")
public class MetricFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricFilter.class);

    @Autowired
    MetricRepository metricRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            time = System.currentTimeMillis() - time;
            metricRepository.save(new Metric(((HttpServletRequest) request).getRequestURI(), ((HttpServletRequest) request).getMethod(), time));
            LOGGER.info("{}: {} ms ", ((HttpServletRequest) request).getRequestURI(),  time);
        }
    }

    @Override
    public void destroy() { }
}