package com.noxinfinity.pdating.Applications.Auth.Login.Service;

import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginRequest;
import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginResponse;
import com.noxinfinity.pdating.Domains.AuthManagement.Auth;
import com.noxinfinity.pdating.Domains.AuthManagement.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthRepo authRepo;
    @Autowired
    public LoginService(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }
    public LoginResponse login(LoginRequest request) {
        Auth auth = authRepo.findUserAuthAndCheckPassword(request.username(), request.password());
        if (auth != null) {
            return new LoginResponse(200,"token");
        }
        return new LoginResponse(401,"unauthorized");
    }
}
