package com.bugboard26.config;

import com.bugboard26.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> jwtFilter(){
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtFilter());
        registration.addUrlPatterns("/api/*");
        return registration;
    }
}