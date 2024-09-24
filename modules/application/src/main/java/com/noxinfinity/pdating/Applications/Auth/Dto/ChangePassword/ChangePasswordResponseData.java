package com.noxinfinity.pdating.Applications.Auth.Dto.ChangePassword;

public class ChangePasswordResponseData {
    private final String data;
    public ChangePasswordResponseData(ChangePasswordResponse changePasswordResponse) {
        this.data = changePasswordResponse.data();
    }

    public String getData() {
        return data;
    }
}
