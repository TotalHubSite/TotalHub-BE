package com.totalhubsite.backend.global.security.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityExceptionResponse {

    private static final Logger log = LoggerFactory.getLogger(SecurityExceptionResponse.class);
    public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // 로그인 실패 JSON 응답을 생성
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", httpStatus);
            errorResponse.put("message", msg);

            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            response.setStatus(httpStatus.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

}
