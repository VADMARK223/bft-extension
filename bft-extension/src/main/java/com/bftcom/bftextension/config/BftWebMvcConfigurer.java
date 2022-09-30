package com.bftcom.bftextension.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Markitanov Vadim
 * @since 21.09.2022
 */
@Configuration
public class BftWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST","PUT", "DELETE");
//        registry.addMapping("/tasks").allowedOrigins("*");
//        registry.addMapping("/config").allowedOrigins("*");
    }
}
