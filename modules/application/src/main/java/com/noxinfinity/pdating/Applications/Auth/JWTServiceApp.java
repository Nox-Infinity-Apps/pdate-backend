package com.noxinfinity.pdating.Applications.Auth;

import com.noxinfinity.pdating.Domains.AuthManagement.JWT.JwtService;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWTServiceApp {

    private final JwtService jwtService;

    @Autowired
    public JWTServiceApp(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Boolean isTokenValid(String token) {
        return jwtService.isTokenValid(token);
    }

    public UserFromGoogle decodeToken(String token) {
        return jwtService.decodeToken(token);
    }
}
