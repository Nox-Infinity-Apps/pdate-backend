package com.noxinfinity.pdating.Primary.Auth;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.noxinfinity.pdate.graphql.types.LoginWithGoogle;
import io.sentry.Sentry;
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
    public LoginByGoogleResponse loginByGoogle(@InputArgument(name = "token") String token) throws Exception {
        try {
            LoginWithGoogle res = authService.loginWithGoogle(token);
            return new LoginByGoogleResponse
                    .Builder()
                    .message("Login google thành công")
                    .status(StatusEnum.SUCCESS)
                    .accessToken(res.getAccessToken())
                    .build();
        }catch (Exception e){
            return new LoginByGoogleResponse
                    .Builder()
                    .message(e.getMessage())
                    .status(StatusEnum.FAILED)
                    .accessToken("")
                    .build();
        }
    }
}
