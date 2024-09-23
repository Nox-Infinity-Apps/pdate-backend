package com.noxinfinity.pdating.Applications.Auth.Dto.Login;

public class LoginResponseData {
    private final String token;
    public LoginResponseData(LoginResponse loginResponse) {
        this.token = loginResponse.token();
    }

    public String getToken() {
        return token;
    }
}
