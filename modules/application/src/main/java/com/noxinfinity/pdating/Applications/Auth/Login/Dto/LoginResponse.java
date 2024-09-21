package com.noxinfinity.pdating.Applications.Auth.Login.Dto;

public class LoginResponse {
    public int statusCode;
    public String token;
    public LoginResponse(int statusCode, String token) {
        this.statusCode = statusCode;
        this.token = token;
    }
}
