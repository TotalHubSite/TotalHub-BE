package com.totalhubsite.backend.global.security.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;
import java.util.Random;
import org.springframework.data.util.Pair;

public class JwtKey {

    // .env 에서 키값 가져오기
    private static Dotenv dotenv = Dotenv.load();
    private static final String JWT_SECRET_KEY1 = dotenv.get("JWT_SECRET_KEY1");
    private static final String JWT_SECRET_KEY2 = dotenv.get("JWT_SECRET_KEY2");
    private static final String JWT_SECRET_KEY3 = dotenv.get("JWT_SECRET_KEY3");

    // 키값 Map에 저장
    private static final Map<String, String> SECRET_KEY_SET = Map.of(
        "key1", JWT_SECRET_KEY1,
        "key2", JWT_SECRET_KEY2,
        "key3", JWT_SECRET_KEY3
    );

    // 키값을 Array로 저장
    // Map -> Set -> Array 순으로 타입변환되어 적용
    private static final String[] KID_SET = SECRET_KEY_SET.keySet().toArray(new String[0]);

    // 랜덤값
    private static Random randomIndex = new Random();

    // 램덤 키id(인덱스), 키값 반환
    // hmacShaKeyFor 통해서 HS256 알고리즘 적용 (키 크기 256비트 이하일 경우 자동적용됨)
    public static Pair<String, Key> getRandomKey() {
        String kid = KID_SET[randomIndex.nextInt(KID_SET.length)];
        String secretKey = SECRET_KEY_SET.get(kid);
        // Keys.hmacShaKeyFor 메소드는 주어진 비밀키를 바탕으로 HMAC-SHA 알고리즘에 사용될 키를 생성
        // 키는 이미 Base64Url 인코딩 되어있음
        return Pair.of(kid, Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    // 키id를 통하여 키값 전달
    public static Key getKey(String kid) {
        String key = SECRET_KEY_SET.getOrDefault(kid, null);
        if (key == null) {
            return null;
        }
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

}
