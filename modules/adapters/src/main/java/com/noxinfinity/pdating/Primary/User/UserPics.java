package com.noxinfinity.pdating.Primary.User;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.Applications.User.IUserApp;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.graphql.types.StatusEnum;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import com.noxinfinity.pdating.graphql.types.UserPicsMutationResponse;
import com.noxinfinity.pdating.graphql.types.UserPicsQueryResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return userApp.uploadPicture(decodedToken.getFcm_id(),file);
        } catch (Exception e){
            return new UserPicsMutationResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public UserPicsMutationResponse updatePictureById(@InputArgument(name = "file") MultipartFile file , @InputArgument(name = "id") String id){
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return userApp.updatePictureById(decodedToken.getFcm_id(),file,id);
        } catch (Exception e){
            return new UserPicsMutationResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsMutation
    @ValidateToken
    public UserPicsMutationResponse deletePictureById(@InputArgument(name = "id") String id){
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return userApp.deletePictureById(decodedToken.getFcm_id(),id);
        } catch (Exception e){
            return new UserPicsMutationResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }

    @DgsQuery
    @ValidateToken
    public UserPicsQueryResponse getUserPics(){
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return userApp.getUserPics(decodedToken.getFcm_id());
        } catch (Exception e){
            return new UserPicsQueryResponse.Builder().message(e.getMessage()).status(StatusEnum.FAILED).build();
        }
    }
}
