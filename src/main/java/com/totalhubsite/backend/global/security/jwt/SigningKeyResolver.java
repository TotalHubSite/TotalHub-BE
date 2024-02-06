package com.totalhubsite.backend.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import java.security.Key;

// JWT 키를 여러개 사용시 지원하는 인터페이스 -
// jwt 토큰의 헤더와 바디의 클레임들을 받아 처리
// 여기서는 헤더의 key id를 통해서 해당하는 키값을 반환하여, 검증에 사용할 수 있게한다.
public class SigningKeyResolver extends SigningKeyResolverAdapter {

    public static SigningKeyResolver instance = new SigningKeyResolver();

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        String kid = jwsHeader.getKeyId();
        if (kid == null) {
            return null;
        }
        return JwtKey.getKey(kid);
    }

}
