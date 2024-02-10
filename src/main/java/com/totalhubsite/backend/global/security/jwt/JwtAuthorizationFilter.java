package com.totalhubsite.backend.global.security.jwt;

import com.totalhubsite.backend.domain.member.repository.MemberRepository;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// 매 요청마다 동작하는 필터
// 발행된 jwt 토큰 인증(인가) 관련

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;

    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException
    {
        //header에서 토큰 가져옴
        List<String> headerValues = Collections.list(request.getHeaders("Authorization"));
        String accessToken = headerValues.stream()
            .findFirst()
            .map(header -> header.replace("Bearer ", ""))
            .orElse(null);

        // 현재 토큰을 사용 하여 인증 및 인증 객체 반환
        Authentication authentication = getUsernamePasswordAuthenticationToken(accessToken);

        // jwt 토큰을 이용해 인증이 확인되었으니 SecurityContextHolder바로 추가
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

    private Authentication getUsernamePasswordAuthenticationToken(String token) {
        if (token == null) {
            return null;
        }

        // getEmail 함수가 인증 후, email 반환
        String email = jwtProvider.getEmail(token);

        // 인증 객체 반환
        if (email != null) {
            return memberRepository.findByEmail(email)
                .map(PrincipalDetails::new)
                .map(principalDetails -> new UsernamePasswordAuthenticationToken(
                    principalDetails, // principal
                    null, // credentials
                    principalDetails.getAuthorities()
                )).orElseThrow(IllegalAccessError::new);
        }
        return null; // 유저가 없으면 NULL
    }


}
