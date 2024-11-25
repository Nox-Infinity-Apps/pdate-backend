package com.noxinfinity.pdating.Domains.AuthManagement.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.noxinfinity.pdating.ThirdServices.StreamChat;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService implements IJwtService {

    private final String secretKey;
    private final long expirationTimeMs;

    private final StreamChat streamChat;

    @Autowired
    public JwtService(String jwtSecretKey, long jwtExpirationTimeMs, StreamChat streamChat) {
        this.secretKey = jwtSecretKey;
        this.expirationTimeMs = jwtExpirationTimeMs;
        this.streamChat = streamChat;
    }

    @Override
    public String generateToken(UserFromGoogle user) throws Exception {
        String streamToken = this.streamChat.signToken(user.getFcm_id());
        return JWT.create()
                .withClaim("id", user.getFcm_id())
                .withClaim("email", user.getEmail())
                .withClaim("fullName", user.getFullName())
                .withClaim("avatar", user.getAvatar())
                .withClaim("provider", user.getProvider())
                .withClaim("streamToken", streamToken)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTimeMs))
                .sign(Algorithm.HMAC256(secretKey));
    }

    @Override
    public Boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public UserFromGoogle decodeToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);

            // Map claims to UserFromGoogle
            String id = decodedJWT.getClaim("id").asString();
            String email = decodedJWT.getClaim("email").asString();
            String fullName = decodedJWT.getClaim("fullName").asString();
            String avatar = decodedJWT.getClaim("avatar").asString();
            String provider = decodedJWT.getClaim("provider").asString();

            return new UserFromGoogle(id, email, fullName, avatar, provider);
        } catch (Exception e) {
            return null;
        }
    }


}
