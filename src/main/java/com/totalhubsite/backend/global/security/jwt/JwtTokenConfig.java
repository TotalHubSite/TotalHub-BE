package com.totalhubsite.backend.global.security.jwt;

// 토큰 속성 설정
public class JwtTokenConfig {

    public static final int ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 40; // 40시간

//    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 40; // 리플래쉬 토큰 설정은 추후에

}
