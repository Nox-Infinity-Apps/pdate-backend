package com.noxinfinity.pdating.Primary.User;

import com.netflix.graphql.dgs.*;
import com.noxinfinity.pdating.Applications.Auth.AuthServices;
import com.noxinfinity.pdating.Applications.User.UserService;
import com.noxinfinity.pdating.GraphQL.Exception.UnauthorizedException;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.Primary.Base.Base;
import com.noxinfinity.pdating.graphql.types.BlockUserResponse;
import com.noxinfinity.pdating.graphql.types.DontCareUserResponse;
import com.noxinfinity.pdating.graphql.types.UserData;
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
    @ValidateToken
    public List<UserSuggest> suggestedUsers(
            @InputArgument double currentLat,
            @InputArgument double currentLng,
            @InputArgument Integer offset
    ) {
        String userId = Base.getUserId();
        try {
            if (offset == null) {
                offset = 0;
            }
            return userService.getSuggestedUsers(userId, currentLat, currentLng, offset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DgsQuery()
    @ValidateToken
    public List<UserData> blockedUsers(){
        String userId = Base.getUserId();
        try {
            return userService.listBlockedUsers(userId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DgsQuery()
    @ValidateToken
    public List<UserSuggest> suggestedUsersWithFilter(
            @InputArgument double currentLat,
            @InputArgument double currentLng,
            @InputArgument Integer offset,
            @InputArgument Integer categoryId
    ){
        String userId = Base.getUserId();
        try {
            return userService.getSuggestedUsersWithFilter(userId, currentLat, currentLng, offset, categoryId);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DgsQuery()
    @ValidateToken
    public List<UserSuggest> suggestedUsersNearBy(
            @InputArgument double currentLat,
            @InputArgument double currentLng,
            @InputArgument Integer offset
    ){
        String userId = Base.getUserId();
        try {
            return userService.getSuggestedUsersNearBy(userId, currentLat, currentLng, offset);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DgsQuery()
    @ValidateToken
    public List<UserData> getLikedUser(){
        String userId = Base.getUserId();
        try {
            return userService.getLikedUser(userId);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DgsMutation()
    @ValidateToken
    public BlockUserResponse blockUser(
            @InputArgument String blockedUserId
    ) throws UnauthorizedException {
        String userId = Base.getUserId();
        try {
            return userService.blockUser(userId, blockedUserId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DgsMutation
    @ValidateToken
    public BlockUserResponse unblockUser(
            @InputArgument String blockedUserId
    ) throws UnauthorizedException {
        String userId = Base.getUserId();
        try {
            return userService.unblockUser(userId, blockedUserId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DgsMutation
    @ValidateToken
    public DontCareUserResponse dontCarekUser(
            @InputArgument String dontCareUserId
    ) throws UnauthorizedException {
        String userId = Base.getUserId();
        try {
            return userService.dontCareUser(userId, dontCareUserId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
