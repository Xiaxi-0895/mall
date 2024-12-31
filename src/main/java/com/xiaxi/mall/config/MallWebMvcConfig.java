package com.xiaxi.mall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MallWebMvcConfig implements WebMvcConfigurer {
    @Value("${file.upload.dir}")
    private String path;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //当访问图片信息时（访问以/images 开头的请求），通过访问path的路径来调用静态资源
        registry.addResourceHandler("images/**").addResourceLocations("file:"+path);
    }
}
