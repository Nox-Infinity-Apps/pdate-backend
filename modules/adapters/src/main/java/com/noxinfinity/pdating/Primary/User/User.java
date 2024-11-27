package com.noxinfinity.pdating.Primary.User;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.User.IUserApp;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.Primary.Base.Base;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@DgsComponent
public class User {
    private final IUserApp userApp;

    @Autowired
    public User(IUserApp userApp) {
        this.userApp = userApp;
    }

    @DgsQuery()
    @ValidateToken
    public UserInfoResponse getUserInfo() {
        try {
            String userId = Base.getUserId();
            return userApp.getUserInfoById(userId);
        } catch (Exception e) {
            return new UserInfoFailedResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation()
    @ValidateToken
    public UserInfoResponse updateUserInfo(@InputArgument(name = "input") UpdateUserInfo input){
        try {
            String userId = Base.getUserId();
            return userApp.updateUserInfoById(userId,input);
        } catch (Exception e){
            return new UserInfoFailedResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public UserInfoSuccessResponse updateFcmTokenAndLocation(@InputArgument(name = "input") UpdateFcmTokenAndLocation input){
        try {
            String userId = Base.getUserId();
            return userApp.updateFcmTokenAndLocation(userId,input);
        } catch (Exception e){
            return new UserInfoSuccessResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public CloudinaryUploadResponse uploadAvatar(@InputArgument(name = "file") MultipartFile file){
        try {
            String userId = Base.getUserId();
            return userApp.uploadAvatar(userId,file);
        } catch (Exception e){
            return new CloudinaryUploadResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public CloudinaryUploadResponse deleteAvatar(){
        try {
            String userId = Base.getUserId();
            return userApp.deleteAvatar(userId);
        } catch (Exception e){
            return new CloudinaryUploadResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsQuery
    @ValidateToken
    public GetAllPurposeResponse getAllPurpose(){
        try {
            String userId = Base.getUserId();
            return userApp.getAllPurpose();
        } catch (Exception e){
            return new GetAllPurposeResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public GetAllPurposeResponse updateUserPurpose(@InputArgument(name = "purposeIds") List<Integer> purposeIds){
        try {
            String userId = Base.getUserId();
            return userApp.updateUserPurpose(userId,purposeIds);
        } catch (Exception e){
            return new GetAllPurposeResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }
}
