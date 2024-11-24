package com.noxinfinity.pdating.Domains.UserDataManagement.UserPics;

import com.noxinfinity.pdating.Domains.UploadManagement.IUploadService;
import com.noxinfinity.pdating.Repository.UserData.IUserDataRepository;
import com.noxinfinity.pdating.graphql.types.CloudinaryUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.noxinfinity.pdating.Entities.UserData;
@Service
public class UserPicsService implements IUserPicsService {

    private final IUserDataRepository _user;
    private final IUploadService _uploadService;

    @Autowired
    public UserPicsService(IUserDataRepository userDataRepository , IUploadService uploadService) {
        this._user = userDataRepository;
        this._uploadService = uploadService;
    }

    @Override
    public String uploadAvatar(String id, MultipartFile file) throws Exception {
        UserData userData = _user.findById(id).orElse(null);
        if (userData == null) {
            throw new Exception("User not found");
        }
        CloudinaryUploadResult url = _uploadService.uploadImage(file);
        userData.setAvatarUrl(url.getUrl());
        userData.setPublicAvataId(url.getPublicId());
        _user.save(userData);
        return url.getUrl();
    }
}
