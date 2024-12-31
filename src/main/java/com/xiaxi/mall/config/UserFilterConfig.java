package com.xiaxi.mall.config;

import com.xiaxi.mall.filter.AdminFilter;
import com.xiaxi.mall.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserFilterConfig {

    @Bean
    public UserFilter userFilter() {
        return new UserFilter();
    }

    @Bean(name = "userFilterConf")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(userFilter());
        filterRegistrationBean.addUrlPatterns("/user/cart/*");
        filterRegistrationBean.addUrlPatterns("/user/order/*");
        filterRegistrationBean.setName("userFilterConfig");
        return filterRegistrationBean;
    }


}
