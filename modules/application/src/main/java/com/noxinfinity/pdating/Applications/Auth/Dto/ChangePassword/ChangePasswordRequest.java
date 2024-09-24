package com.noxinfinity.pdating.Applications.Auth.Dto.ChangePassword;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChangePasswordRequest {
        @NotNull
        @NotBlank
        public String oldPassword;
        @NotNull
        @NotBlank
        public String newPassword;
}
