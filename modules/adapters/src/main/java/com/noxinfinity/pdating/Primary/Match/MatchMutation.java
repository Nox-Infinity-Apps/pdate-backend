package com.noxinfinity.pdating.Primary.Match;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.Auth.AuthServices;
import com.noxinfinity.pdating.GraphQL.Exception.UnauthorizedException;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.Primary.Base.Base;
import com.noxinfinity.pdating.graphql.types.LikeUserResponse;
import com.noxinfinity.pdating.graphql.types.UnLikeUserResponse;
import com.noxinfinity.pdating.Applications.Match.MatchServices;
import org.springframework.beans.factory.annotation.Autowired;


@DgsComponent
public class MatchMutation {
    private final MatchServices matchServices;
    private final AuthServices authServices;

    @Autowired
    public MatchMutation(MatchServices matchServices, AuthServices authServices) {
        this.matchServices = matchServices;
        this.authServices = authServices;
    }

    @DgsMutation()
    @ValidateToken
    public LikeUserResponse like(@InputArgument(name = "targetUserId") String targetUserId) throws UnauthorizedException {
        String userId = Base.getUserId();
        try {
            return matchServices.like(userId, targetUserId);
        } catch (Exception e) {
            throw new RuntimeException("Error processing like request", e);
        }
    }

    @DgsMutation()
    @ValidateToken
    public UnLikeUserResponse unlike(@InputArgument(name = "targetUserId") String targetUserId) throws UnauthorizedException {
        String userId = Base.getUserId();
        try {
            return matchServices.unlike(userId, targetUserId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
