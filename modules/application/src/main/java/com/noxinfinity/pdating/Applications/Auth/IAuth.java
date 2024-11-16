package com.noxinfinity.pdating.Applications.Auth;

import com.noxinfinity.pdating.graphql.types.LoginWithGoogle;

public interface IAuth {
    LoginWithGoogle loginWithGoogle(String token) throws Exception;
    String loginWithApple(String token);
}
