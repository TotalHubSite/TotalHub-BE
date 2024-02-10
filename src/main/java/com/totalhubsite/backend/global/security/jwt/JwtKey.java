package com.totalhubsite.backend.global.security.jwt;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class JwtKey {

    private final Map<String, String> SECRET_KEY_SET;
    private final String[] KID_SET;
    private final Random randomIndex = new Random(); // 랜덤값

    public JwtKey(JwtProperties jwtProperties) {
        this.SECRET_KEY_SET = Map.of(
            "key1", jwtProperties.getJwtSecretKey1(),
            "key2", jwtProperties.getJwtSecretKey2(),
            "key3", jwtProperties.getJwtSecretKey3()
        );
        this.KID_SET = SECRET_KEY_SET.keySet().toArray(new String[0]);
    }

    // 램덤 키id(인덱스), 키값 반환
    // hmacShaKeyFor 통해서 HS256 알고리즘 적용 (키 크기 256비트 이하일 경우 자동적용됨)
    public Pair<String, Key> getRandomKey() {
        String kid = KID_SET[randomIndex.nextInt(KID_SET.length)];
        String secretKey = SECRET_KEY_SET.get(kid);
        // Keys.hmacShaKeyFor 메소드는 주어진 비밀키를 바탕으로 HMAC-SHA 알고리즘에 사용될 키를 생성
        // 키는 이미 Base64Url 인코딩 되어있음
//        return Pair.of(kid, Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)));
        byte[] decodedKey = Base64.getUrlDecoder().decode(secretKey);
        return Pair.of(kid, Keys.hmacShaKeyFor(decodedKey));
    }

    // 키id를 통하여 키값 전달
    public Key getKey(String kid) {
        String key = SECRET_KEY_SET.getOrDefault(kid, null);
        if (key == null) {
            return null;
        }
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

}
