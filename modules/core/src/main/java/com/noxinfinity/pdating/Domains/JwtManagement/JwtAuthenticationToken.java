package com.noxinfinity.pdating.Domains.JwtManagement;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;

    public JwtAuthenticationToken(String username, List<String> roles) {
        super(roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.username = username;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }
}