package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.Domains.UserDataManagement.UserData.IUserDataService;
import com.noxinfinity.pdating.Domains.UserDataManagement.UserPics.IUserPicsService;
import com.noxinfinity.pdating.graphql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

}
