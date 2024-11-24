package com.noxinfinity.pdating.Primary.User;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.User.IUserApp;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.GraphQL.Scalars.UploadScalar;
import com.noxinfinity.pdating.graphql.types.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

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
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return userApp.getUserInfoById(decodedToken.getFcm_id());
        } catch (Exception e) {
            return new UserInfoFailedResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation()
    @ValidateToken
    public UserInfoResponse updateUserInfo(@InputArgument(name = "input") UpdateUserInfo input){
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return userApp.updateUserInfoById(decodedToken.getFcm_id(),input);
        } catch (Exception e){
            return new UserInfoFailedResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public CloudinaryUploadResponse uploadAvatar(@InputArgument(name = "file") MultipartFile file){
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return userApp.uploadAvatar(decodedToken.getFcm_id(),file);
        } catch (Exception e){
            return new CloudinaryUploadResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }
}
