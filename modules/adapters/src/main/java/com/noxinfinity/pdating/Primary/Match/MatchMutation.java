package com.noxinfinity.pdating.Primary.Match;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.Auth.AuthServices;
import com.noxinfinity.pdating.GraphQL.Exception.UnauthorizedException;
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
    public LikeUserResponse like(@InputArgument(name = "targetUserId") String targetUserId, DgsDataFetchingEnvironment dfe) throws UnauthorizedException {
        String token = Base.extractTokenFromDfe(dfe);
        try {
            return matchServices.like(authServices.getUserFromToken(token).getFcm_id(), targetUserId);
        } catch (Exception e) {
            throw new RuntimeException("Error processing like request", e);
        }
    }

    @DgsMutation()
    public UnLikeUserResponse unlike(@InputArgument(name = "targetUserId") String targetUserId,DgsDataFetchingEnvironment dfe) throws UnauthorizedException {
        String token = Base.extractTokenFromDfe(dfe);
        try {
            return matchServices.unlike(authServices.getUserFromToken(token).getFcm_id(), targetUserId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
