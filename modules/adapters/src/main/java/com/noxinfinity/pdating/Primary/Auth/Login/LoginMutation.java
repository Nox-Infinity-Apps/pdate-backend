package com.noxinfinity.pdating.Primary.Auth.Login;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginRequest;
import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginResponse;
import com.noxinfinity.pdating.Applications.Auth.Login.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class LoginMutation {
    private final LoginService loginService;
    @Autowired
    public LoginMutation(LoginService loginService) {
        this.loginService = loginService;
    }
    @DgsMutation
    public Response_Login login(LoginRequest loginRequest) {
        LoginResponse response = loginService.login(loginRequest);
        return new Response_Login(response.statusCode,response.token);
    }
}
