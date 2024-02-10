package com.totalhubsite.backend.global.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.jwt-key")
@Getter
@Setter
public class JwtProperties {

    private String jwtSecretKey1;
    private String jwtSecretKey2;
    private String jwtSecretKey3;

}
