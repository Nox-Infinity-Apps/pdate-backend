package com.noxinfinity.pdating.Applications.Auth.Dto.Register;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest{
    @NotBlank
    @NotNull
    public String username;
    @NotBlank
    @NotNull
    public String password;
}