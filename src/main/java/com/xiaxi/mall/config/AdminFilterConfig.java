package com.xiaxi.mall.config;

import com.xiaxi.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminFilterConfig {
    @Bean
    public AdminFilter adminFilter() {
        return new AdminFilter();
    }

    @Bean(name = "adminFilterConf")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(adminFilter());
        filterRegistrationBean.addUrlPatterns("/admin/category/*");
        filterRegistrationBean.addUrlPatterns("/admin/goods/*");
        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterRegistrationBean.addUrlPatterns("/admin/upload/*");
        filterRegistrationBean.setName("adminFilterConfig");
        return filterRegistrationBean;
    }
}
