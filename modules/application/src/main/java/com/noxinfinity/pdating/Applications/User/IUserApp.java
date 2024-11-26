package com.noxinfinity.pdating.Applications.User;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.web.multipart.MultipartFile;

public interface IUserApp {
    UserInfoSuccessResponse getUserInfoById(String id);
    UserInfoSuccessResponse updateUserInfoById(String id, UpdateUserInfo body) throws Exception;
    CloudinaryUploadResponse uploadAvatar(String id, MultipartFile file) throws Exception;
    CloudinaryUploadResponse deleteAvatar(String id) throws Exception;
    UserInfoSuccessResponse updateFcmTokenAndLocation(String id, UpdateFcmTokenAndLocation input) throws Exception;

    //UserPics
    UserPicsMutationResponse uploadPicture(String id, MultipartFile file) throws Exception;
    UserPicsMutationResponse updatePictureById(String id, MultipartFile file, String picId) throws Exception;
    UserPicsMutationResponse deletePictureById(String id, String picId) throws Exception;
    UserPicsQueryResponse getUserPics(String id) throws Exception;
}
