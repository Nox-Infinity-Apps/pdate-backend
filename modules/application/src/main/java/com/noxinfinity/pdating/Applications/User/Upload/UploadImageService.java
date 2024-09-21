package com.noxinfinity.pdating.Applications.User.Upload;

import com.noxinfinity.pdating.Implementations.Cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadImageService {
    private final CloudinaryService  cloudinaryService;

    @Autowired
    public UploadImageService(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    public String upload(MultipartFile file) throws IOException {
        Map<?,?> result = this.cloudinaryService.upload(file, "avatar");
        return result.toString();
    }
}
