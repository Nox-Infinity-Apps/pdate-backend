package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.Domains.UserDataManagement.UserData.IUserDataService;
import com.noxinfinity.pdating.Domains.UserDataManagement.UserPics.IUserPicsService;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserApp implements IUserApp {

    private final IUserDataService userDataService;
    private final IUserPicsService userPicsService;

    @Autowired
    public UserApp(IUserDataService userDataService, IUserPicsService userPicsService) {
        this.userDataService = userDataService;
        this.userPicsService = userPicsService;
    }

    public UserInfoSuccessResponse getUserInfoById(String id) {
        UserData userData = userDataService.getUserDataById(id);
        return new UserInfoSuccessResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(userData).build();
    }

    @Override
    public UserInfoSuccessResponse updateUserInfoById(String id, UpdateUserInfo body) throws Exception {
        UserData userData = userDataService.updateUserDataById(id,body);
        return new UserInfoSuccessResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(userData).build();
    }

    @Override
    public CloudinaryUploadResponse uploadAvatar(String id, MultipartFile file) throws Exception {
        String url = userPicsService.uploadAvatar(id,file);
        return new CloudinaryUploadResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(url).build();
    }

    @Override
    public CloudinaryUploadResponse deleteAvatar(String id) throws Exception {
        String message = userPicsService.deleteAvatar(id);
        return new CloudinaryUploadResponse.Builder().message(message).status(StatusEnum.SUCCESS).data(message).build();
    }

    @Override
    public UserInfoSuccessResponse updateFcmTokenAndLocation(String id, UpdateFcmTokenAndLocation input) throws Exception {
        UserData userData = userDataService.updateFcmTokenAndLocation(id,input);
        return new UserInfoSuccessResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(userData).build();
    }

    @Override
    public UserPicsMutationResponse uploadPicture(String id, MultipartFile file) throws Exception {
        PictureData data = userPicsService.uploadPicture(id,file);
        return new UserPicsMutationResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(data).build();
    }

    @Override
    public UserPicsMutationResponse updatePictureById(String id, MultipartFile file, String picId) throws Exception {
        PictureData data = userPicsService.updatePictureById(id,file,picId);
        return new UserPicsMutationResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(data).build();
    }

    @Override
    public UserPicsMutationResponse deletePictureById(String id, String picId) throws Exception {
        String message = userPicsService.deletePictureById(id,picId);
        return new UserPicsMutationResponse.Builder().message(message).status(StatusEnum.SUCCESS).build();
    }

    @Override
    public UserPicsQueryResponse getUserPics(String id) throws Exception {
        List<PictureData> data = userPicsService.getUserPics(id);
        return new UserPicsQueryResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(data).build();
    }

    @Override
    public GetAllPurposeResponse getAllPurpose() throws Exception {
        List<PurposeResponse> data = userDataService.getAllPurpose();
        return new GetAllPurposeResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(data).build();
    }

    @Override
    public GetAllPurposeResponse updateUserPurpose(String id, List<Integer> purposeIds) throws Exception {
        List<PurposeResponse> data = userDataService.updateUserPurpose(id,purposeIds);
        return new GetAllPurposeResponse.Builder().message("Success").status(StatusEnum.SUCCESS).data(data).build();
    }

}
