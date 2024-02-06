package com.totalhubsite.backend.global.security.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.totalhubsite.backend.domain.member.dto.request.SignInRequestDto;
import com.totalhubsite.backend.domain.member.dto.response.SignInResponseDto;
import com.totalhubsite.backend.domain.member.entity.Member;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

// 로그인 요청 시 동작하는 필터 구현 (UsernamePasswordAuthenticationFilter가 본래 로그인 처리)
// 로그인 시 JWT 발행에 대한 처리

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(
        AuthenticationManager authenticationManager,
        JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    // 로그인 인증 시도 (AuthenticationManager에게 인증 요청 사용자 정보 전달)
    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {
        try {
            // 요청된 JSON 데이터를 Dto객체로 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            SignInRequestDto signInRequestDto = objectMapper.readValue(request.getInputStream(),
                SignInRequestDto.class);

            // 로그인할 때 입력한 email과 password를 가지고 authenticationToken를 생성
            // 인증 절차이니 DTO 역할인 UsernamePasswordAuthenticationToken에 아이디, 비밀번호만
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                signInRequestDto.email(),
                signInRequestDto.password()
            );

            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 인증 성공 시
    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult
    ) throws IOException {
        Member member = ((PrincipalDetails) authResult.getPrincipal()).getMember();
        String token = jwtProvider.createToken(member);

        SignInResponseDto signInResponseDto = SignInResponseDto.fromEntity(member, token);

        sendJsonResponse(response, signInResponseDto, HttpStatus.OK);
    }

    // 인증 실패시
    @Override
    protected void unsuccessfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException, ServletException {
        String authenticationErrorMessage = getAuthenticationErrorMessage(exception);
        sendJsonResponse(response, authenticationErrorMessage, HttpStatus.BAD_REQUEST);
    }


    private void sendJsonResponse(HttpServletResponse response, Object responseData,
        HttpStatus httpStatus)
        throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String jsonResponse = objectMapper.writeValueAsString(responseData);

        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private String getAuthenticationErrorMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return exception.getMessage() + "이메일 또는 비밀번호 에러";
        } else if (exception instanceof UsernameNotFoundException) {
            return exception.getMessage() + "존재하지 않는 유저";
        } else {
            return exception.getMessage() + "인증 실패";
        }
    }


}
