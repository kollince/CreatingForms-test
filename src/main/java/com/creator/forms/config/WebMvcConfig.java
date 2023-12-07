package com.creator.forms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**", "/data/**")
                .addResourceLocations("file:///"+System.getProperty("user.home")+File.separator+"forms/images"+File.separator,
                        "file:///"+System.getProperty("user.home")+ File.separator+"forms/data"+File.separator)
                .setCachePeriod(0);
    }

}
