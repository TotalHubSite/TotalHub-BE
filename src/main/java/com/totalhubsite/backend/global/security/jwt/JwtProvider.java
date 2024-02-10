package com.totalhubsite.backend.global.security.jwt;

import com.totalhubsite.backend.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtKey jwtKey;
    private final SigningKeyResolver signingKeyResolver;

    // 토큰 검증 및 토큰에서 email 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder()
            .setSigningKeyResolver(signingKeyResolver) // 헤더의 kid로 해당 키값 가져오기
            .build()
            .parseClaimsJws(token) // 위에 setSigningKeyResolver로 얻어낸 키값 통해 토큰 검증
            .getBody()
            .getSubject();
    }

    // 토큰 생성
    public String createToken(Member member) {
        Claims claims = Jwts.claims().setSubject(member.getEmail()); // subject - email 지정
        Date now = new Date(); // 현재 시간
        Pair<String, Key> key = jwtKey.getRandomKey(); // 랜덤한 키값 지정
        // JWT Token 생성
        return Jwts.builder()
            .setClaims(claims) // 클레임 설정
            .setIssuedAt(now)  // 토큰 발행 시간 설정
            .setExpiration(
                new Date(now.getTime() + JwtTokenConfig.ACCESS_TOKEN_EXPIRATION_TIME)) // 토큰 만료 시간 설정
            .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid(키 id) 설정
            .signWith(key.getSecond()) // signature 설정(램덤 선택되고 알고리즘 적용된 key 값 적용하여)
            .compact();
    }

}
