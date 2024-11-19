package com.noxinfinity.pdating.Domains.AuthManagement.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService implements IJwtService {

    private final String secretKey;
    private final long expirationTimeMs;

    @Autowired
    public JwtService(String jwtSecretKey, long jwtExpirationTimeMs) {
        this.secretKey = jwtSecretKey;
        this.expirationTimeMs = jwtExpirationTimeMs;
    }

    @Override
    public String generateToken(UserFromGoogle user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("fullName", user.getFullName())
                .withClaim("avatar", user.getAvatar())
                .withClaim("provider", user.getProvider())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTimeMs))
                .sign(Algorithm.HMAC256(secretKey));
    }
}
