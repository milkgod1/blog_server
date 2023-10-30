package com.pro.blog.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;


public class WebfluxConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8000")
                .allowedHeaders("GET", "POST", "PUT", "DELETE")
                .maxAge(3600);
    }
}
