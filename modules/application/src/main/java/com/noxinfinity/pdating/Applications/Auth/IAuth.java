package com.noxinfinity.pdating.Applications.Auth;

public interface IAuth {
    String loginWithGoogle(String token);
    String loginWithApple(String token);
}
