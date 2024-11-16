package com.noxinfinity.pdating.Primary.Auth;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdate.graphql.types.LoginByGoogleResponse;
import com.noxinfinity.pdate.graphql.types.StatusEnum;

@DgsComponent
public class AuthMutation {
    @DgsMutation()
    public LoginByGoogleResponse loginByGoogle(@InputArgument(name = "token") String token) {
        return new LoginByGoogleResponse
                .Builder()
                .message("??? reload dayyyy??")
                .status(StatusEnum.FAILED)
                .build();
    }
}
