package com.noxinfinity.pdating.Domains.AuthManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public Auth getById(String userId){
        return authRepo.findByUserId(UUID.fromString(userId));
    }

    public boolean checkPassword(String userId, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, authRepo.findByUserId(UUID.fromString(userId)).getPassword());
    }
    public boolean updatePasswordByUserId(String userId,String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPassword = passwordEncoder.encode(password);
        return authRepo.updatePasswordByUserId(newPassword,UUID.fromString(userId)) > 0;
    }
}
