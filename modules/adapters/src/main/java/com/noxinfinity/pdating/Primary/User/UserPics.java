package com.noxinfinity.pdating.Primary.User;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.User.IUserApp;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.Primary.Base.Base;
import com.noxinfinity.pdating.graphql.types.StatusEnum;
import com.noxinfinity.pdating.graphql.types.UserPicsMutationResponse;
import com.noxinfinity.pdating.graphql.types.UserPicsQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@DgsComponent
public class UserPics {
    private final IUserApp userApp;

    @Autowired
    public UserPics(IUserApp userApp) {
        this.userApp = userApp;
    }

    @DgsMutation
    @ValidateToken
    public UserPicsMutationResponse uploadPicture(@InputArgument(name = "file") MultipartFile file){
        try {
            String userId = Base.getUserId();
            return userApp.uploadPicture(userId,file);
        } catch (Exception e){
            return new UserPicsMutationResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public UserPicsMutationResponse updatePictureById(@InputArgument(name = "file") MultipartFile file , @InputArgument(name = "id") String id){
        try {
            String userId = Base.getUserId();
            return userApp.updatePictureById(userId,file,id);
        } catch (Exception e){
            return new UserPicsMutationResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public UserPicsMutationResponse deletePictureById(@InputArgument(name = "id") String id){
        try {
            String userId = Base.getUserId();
            return userApp.deletePictureById(userId,id);
        } catch (Exception e){
            return new UserPicsMutationResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsQuery
    @ValidateToken
    public UserPicsQueryResponse getUserPics(){
        try {
            String userId = Base.getUserId();
            return userApp.getUserPics(userId);
        } catch (Exception e){
            return new UserPicsQueryResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }
}
