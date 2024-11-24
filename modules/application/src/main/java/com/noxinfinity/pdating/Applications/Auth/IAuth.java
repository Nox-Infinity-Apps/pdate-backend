package com.noxinfinity.pdating.Applications.Auth;

import com.noxinfinity.pdate.graphql.types.LoginWithGoogle;

public interface IAuth {
    LoginWithGoogle loginWithGoogle(String token);
    String loginWithApple(String token);
}
