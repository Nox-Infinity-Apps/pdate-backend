package com.noxinfinity.pdating.Primary.Auth;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdate.graphql.types.LoginByGoogleResponse;
import com.noxinfinity.pdate.graphql.types.StatusEnum;
import com.noxinfinity.pdating.Applications.Auth.IAuth;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class AuthMutation {
    private IAuth authService;
    @Autowired
    public AuthMutation(IAuth authService){
        this.authService = authService;
    }

    @DgsMutation()
    public LoginByGoogleResponse loginByGoogle(@InputArgument(name = "token") String token) {
        String res = authService.loginWithGoogle(token);
        return new LoginByGoogleResponse
                .Builder()
                .message("??? reload dayyyy??")
                .status(StatusEnum.FAILED)
                .token(res)
                .build();
    }
}
