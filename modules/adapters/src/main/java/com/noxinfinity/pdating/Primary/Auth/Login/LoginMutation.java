package com.noxinfinity.pdating.Primary.Auth.Login;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginRequest;
import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginResponse;
import com.noxinfinity.pdating.Applications.Auth.Login.Service.LoginService;
import com.noxinfinity.pdating.Primary.Base.Response;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class LoginMutation {
    private final LoginService loginService;
    @Autowired
    public LoginMutation(LoginService loginService) {
        this.loginService = loginService;
    }
    @DgsMutation
    public Response<Object> login(@InputArgument LoginRequest loginRequest) {
        LoginResponse response = loginService.login(loginRequest);
        return new Response<>(200,"success",response,"login successful");
    }
}
