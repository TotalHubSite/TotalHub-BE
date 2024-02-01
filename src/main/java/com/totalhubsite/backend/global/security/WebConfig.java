package com.totalhubsite.backend.global.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://15.165.144.39") // 허용할 출처 설정
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 모든 HTTP 메소드 허용
            .allowedHeaders("*") // 모든 요청 헤더 허용
            .exposedHeaders("*") // 모든 응답 헤더 노출
            .allowCredentials(true); // 쿠키 및 자격증명 정보 전송 허용
    }
}

