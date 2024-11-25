package com.noxinfinity.pdating.Domains.UserDataManagement.UserPics;

import com.noxinfinity.pdating.graphql.types.PictureData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserPicsService {
    String uploadAvatar(String id, MultipartFile file) throws Exception;
    String deleteAvatar(String id) throws Exception;

    //UserPics
    PictureData uploadPicture(String id, MultipartFile file) throws Exception;
    PictureData updatePictureById(String id, MultipartFile file, String picId) throws Exception;
    String deletePictureById(String id, String picId) throws Exception;
    List<PictureData> getUserPics(String id) throws Exception;
}
