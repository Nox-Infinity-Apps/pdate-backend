package com.noxinfinity.pdating.Primary.User;

import com.netflix.graphql.dgs.*;
import com.noxinfinity.pdating.Applications.Auth.AuthServices;
import com.noxinfinity.pdating.Applications.User.UserService;
import com.noxinfinity.pdating.GraphQL.Exception.UnauthorizedException;
import com.noxinfinity.pdating.Primary.Base.Base;
import com.noxinfinity.pdating.graphql.types.UserSuggest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class UserMutation {

    private final UserService userService;
    private final AuthServices authServices;

    @Autowired
    public UserMutation(UserService userService, AuthServices authServices) {
        this.userService = userService;
        this.authServices = authServices;
    }

    @DgsQuery()
    public List<UserSuggest> suggestedUsers(
            @InputArgument double currentLat,
            @InputArgument double currentLng,
            @InputArgument int limit,
            @InputArgument int offset,
            DgsDataFetchingEnvironment dfe
    ) throws UnauthorizedException {
        String token = Base.extractTokenFromDfe(dfe);
        try {
            return userService.getSuggestedUsers(authServices.loginWithGoogle(token).getUser().getFcm_id(), currentLat, currentLng, limit, offset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
