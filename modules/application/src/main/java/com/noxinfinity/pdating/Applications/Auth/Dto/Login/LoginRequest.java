package com.noxinfinity.pdating.Applications.Auth.Dto.Login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
        @NotBlank
        @NotNull
        public String username;
        @NotBlank
        @NotNull
        public String password;
}
