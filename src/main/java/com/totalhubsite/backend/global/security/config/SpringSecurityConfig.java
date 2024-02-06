package com.totalhubsite.backend.global.security.config;

import com.totalhubsite.backend.global.security.dto.SecurityExceptionResponse;
import com.totalhubsite.backend.global.security.jwt.JwtAuthenticationFilter;
import com.totalhubsite.backend.global.security.jwt.JwtAuthorizationFilter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // CORS 설정
        httpSecurity.cors(cors ->
            cors.configurationSource(request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.applyPermitDefaultValues(); // 기본 cors 설정 적용
                corsConfiguration.addAllowedOriginPattern("http://localhost:3000");
                corsConfiguration.addAllowedOriginPattern("http://15.165.144.39");
                corsConfiguration.setAllowedMethods(
                    Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD")
                );

                corsConfiguration.setAllowCredentials(true); // 쿠키를 포함한 크로스 도메인 요청을 허용
                return corsConfiguration;
            }));

        // basic authentication (헤더에 인증정보 전송 - jwt 사용하니 비활성화)
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable); // 비 활성화

        // csrf (헤더에 csrf 토큰 필요 - 개발 용이를 위해, 그리고 RESTful API 구현이기에 비활성화)
        httpSecurity.csrf(AbstractHttpConfigurer::disable); // 비 활성화

        // remember-me (로그인 유지 기능 - jwt 사용하니 비활성화)
        httpSecurity.rememberMe(AbstractHttpConfigurer::disable); // 비 활성화

        // stateless (세션 기능 비활성화 - jwt 사용하니 비활성화)
        httpSecurity.sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT 관련 필터 추가
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class);

        // authorization  권한 설정
        httpSecurity.authorizeHttpRequests(auth -> auth
//            .requestMatchers("/v1/tests").permitAll()
            .requestMatchers("/v1/boards").permitAll()
            .requestMatchers("/v1/posts").permitAll()
            .requestMatchers("/v1/comments").permitAll()
            .requestMatchers("/v1/signup").permitAll()
            .anyRequest().authenticated() // 위의 설정을 제외하고는 권한을 필요로 함
        );

        // security 관련 예외처리 (내용이 많아지면 별도의 클래스로 구현, 여기선 람다식으로 간단히 구현)
        httpSecurity.exceptionHandling(exceptionHandling -> {
            // 인증 관련 예외
            exceptionHandling.authenticationEntryPoint(
                (request, response, authException) -> SecurityExceptionResponse.fail(response,
                    "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED));
            // 인가 관련 예외
            exceptionHandling.accessDeniedHandler(
                (request, response, accessDeniedException) -> SecurityExceptionResponse.fail(response,
                    "접근 권한이 없습니다", HttpStatus.FORBIDDEN));
        });

        return httpSecurity.build();

    }

}
