package com.noxinfinity.pdating.Domains.UserDataManagement.UserPics;

import org.springframework.web.multipart.MultipartFile;

public interface IUserPicsService {
    String uploadAvatar(String id, MultipartFile file) throws Exception;
}
