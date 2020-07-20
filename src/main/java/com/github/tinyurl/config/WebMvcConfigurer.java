package com.github.tinyurl.config;

import com.github.tinyurl.controller.filter.SignFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * TODO 文件说明
 *
 * @author jiquanxi
 * @date 2020/07/20
 */
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Resource
    private SignFilter signFilter;

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(signFilter);
        registration.addUrlPatterns("/shorten");
        registration.setName("signFilter");
        registration.setOrder(1);
        return registration;
    }
}
