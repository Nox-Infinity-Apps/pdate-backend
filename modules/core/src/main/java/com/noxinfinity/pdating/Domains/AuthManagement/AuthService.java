package com.noxinfinity.pdating.Domains.AuthManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final AuthRepo authRepo;

    @Autowired
    public AuthService(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    public Auth save(String username, String password, List<String> roles) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        return authRepo.save(new Auth(username, encodedPassword, roles));
    }

    public Auth get(String username) {
        return authRepo.findByUsername(username);
    }
}
