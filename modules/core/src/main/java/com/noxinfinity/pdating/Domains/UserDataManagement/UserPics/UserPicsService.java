package com.noxinfinity.pdating.Domains.UserDataManagement.UserPics;

import com.noxinfinity.pdating.Domains.UploadManagement.IUploadService;
import com.noxinfinity.pdating.Entities.UserPics;
import com.noxinfinity.pdating.Repository.Other.IUserPicsRespository;
import com.noxinfinity.pdating.Repository.UserData.IUserDataRepository;
import com.noxinfinity.pdating.graphql.types.CloudinaryUploadResult;
import com.noxinfinity.pdating.graphql.types.PictureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.noxinfinity.pdating.Entities.UserData;

import java.util.List;

@Service
public class UserPicsService implements IUserPicsService {

    private final IUserDataRepository _user;
    private final IUploadService _uploadService;
    private final IUserPicsRespository _userPicsRepository;

    @Autowired
    public UserPicsService(IUserDataRepository userDataRepository , IUploadService uploadService , IUserPicsRespository userPicsRespository){
        this._user = userDataRepository;
        this._uploadService = uploadService;
        this._userPicsRepository = userPicsRespository;
    }

    public String uploadAvatar(String id, MultipartFile file) throws Exception {
        UserData userData = _user.findById(id).orElse(null);
        if (userData == null) {
            throw new Exception("User not found");
        }

        // Xóa avatar cũ
        if (userData.getPublicAvataId() != null && !userData.getPublicAvataId().isEmpty()) {
            boolean isDeleted = _uploadService.deleteImage(userData.getPublicAvataId());
            if (!isDeleted) {
                throw new Exception("Failed to delete old avatar");
            }
        }

        // Upload avatar mới
        CloudinaryUploadResult url = _uploadService.uploadImage(file);
        if (url == null || url.getUrl() == null || url.getPublicId() == null) {
            throw new Exception("Failed to upload new avatar");
        }

        // Cập nhật thông tin người dùng
        userData.setAvatarUrl(url.getUrl());
        userData.setPublicAvataId(url.getPublicId());
        _user.save(userData);
        return url.getUrl();
    }

    @Override
    public String deleteAvatar(String id) throws Exception {
        UserData userData = _user.findById(id).orElse(null);
        if (userData == null) {
            throw new Exception("User not found");
        }
        if (userData.getPublicAvataId() != null && !userData.getPublicAvataId().isEmpty()) {
            _uploadService.deleteImage(userData.getPublicAvataId());
        }
        userData.setAvatarUrl("");
        userData.setPublicAvataId("");
        _user.save(userData);
        return "Delete avatar success";
    }

    @Override
    public PictureData uploadPicture(String id, MultipartFile file) throws Exception {
        UserData userData = _user.findById(id).orElse(null);
        if (userData == null) {
            throw new Exception("User not found");
        }
        if(userData.getUserPics().size() >=12){
            throw new Exception("User can only upload 12 pictures");
        }
        CloudinaryUploadResult url = _uploadService.uploadImage(file);
        UserPics userPics = new UserPics();
        userPics.setPublicId(url.getPublicId());
        userPics.setImageUrl(url.getUrl());
        userPics.setUserData(userData);
        userPics = _userPicsRepository.save(userPics);
        userData.getUserPics().add(userPics);
        _user.save(userData);
        return new PictureData.Builder().id(userPics.getId().toString()).url(userPics.getImageUrl()).build();
    }

    @Override
    public PictureData updatePictureById(String id, MultipartFile file, String picId) throws Exception {
        UserData userData = _user.findById(id).orElse(null);
        if (userData == null) {
            throw new Exception("User not found");
        }
        UserPics userPics = userData.getUserPics().stream().filter(x -> x.getId().toString().equals(picId)).findFirst().orElse(null);
        if (userPics == null) {
            throw new Exception("Picture not found");
        }
        CloudinaryUploadResult url = _uploadService.uploadImage(file);
        _uploadService.deleteImage(userPics.getPublicId());
        userPics.setPublicId(url.getPublicId());
        userPics.setImageUrl(url.getUrl());
        _userPicsRepository.save(userPics);
        _user.save(userData);
        return new PictureData.Builder().id(userPics.getId().toString()).url(userPics.getImageUrl()).build();
    }

    @Override
    public String deletePictureById(String id, String picId) throws Exception {
        UserData userData = _user.findById(id).orElse(null);
        if (userData == null) {
            throw new Exception("User not found");
        }
        UserPics userPics = userData.getUserPics().stream().filter(x -> x.getId().toString().equals(picId)).findFirst().orElse(null);
        if (userPics == null) {
            throw new Exception("Picture not found");
        }
        _uploadService.deleteImage(userPics.getPublicId());
        userData.getUserPics().remove(userPics);
        _user.save(userData);
        return "Delete picture success";
    }

    @Override
    public List<PictureData> getUserPics(String id) throws Exception {
        UserData userData = _user.findById(id).orElse(null);
        if (userData == null) {
            throw new Exception("User not found");
        }
        return userData.getUserPics().stream().map(x -> new PictureData(x.getImageUrl(),x.getId().toString())).toList();
    }
}
