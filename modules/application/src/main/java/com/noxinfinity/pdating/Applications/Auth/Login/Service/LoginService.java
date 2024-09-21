package com.noxinfinity.pdating.Applications.Auth.Login.Service;

import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginRequest;
import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginResponse;
import com.noxinfinity.pdating.Repository.AuthRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthRepoImpl authRepo;
    @Autowired
    public LoginService(AuthRepoImpl authRepo) {
        this.authRepo = authRepo;
    }
    public LoginResponse login(LoginRequest request) {
        if (authRepo.findUserAuthAndCheckPassword(request.username(), request.password())) {
            return new LoginResponse(200,"token");
        }
        return new LoginResponse(401,"unauthorized");
    }
}
