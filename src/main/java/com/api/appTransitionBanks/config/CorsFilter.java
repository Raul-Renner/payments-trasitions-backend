//package com.api.appTransitionBanks.config;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//@Configuration
//public class CorsFilter {
//    @Bean
//    public org.springframework.web.filter.CorsFilter corsFilter() {
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowCredentials(true); // you USUALLY want this
//        config.addAllowedOrigin("*");
//        config.addAllowedOrigin("http://localhost:4200");
//        config.addAllowedOrigin("http://localhost:8080");
//        config.addAllowedHeader("*");
//        config.addAllowedHeader("Access-Control-Allow-Headers");
//        config.addAllowedHeader("Access-Control-Allow-Origin");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/api/**", config);
//        return new org.springframework.web.filter.CorsFilter(source);
//    }
//}
