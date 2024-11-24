package com.noxinfinity.pdating.Configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {

    // 1 ngày (đơn vị: ms)
    private static final long EXPIRATION_TIME_MS = 86400000L;

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public String jwtSecretKey() {
        return secretKey;
    }

    @Bean
    public long jwtExpirationTimeMs() {
        return EXPIRATION_TIME_MS;
    }
}
