package com.noxinfinity.pdating.Applications.User.Upload;

import com.noxinfinity.pdating.Domains.Cloudinary.CloudinaryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadImageService {
    private final CloudinaryInterface cloudinaryInterface;

    @Autowired
    public UploadImageService(CloudinaryInterface cloudinaryInterface) {
        this.cloudinaryInterface = cloudinaryInterface;
    }

    public String upload(MultipartFile file) throws IOException {
        Map<?,?> result = this.cloudinaryInterface.upload(file, "avatar");
        return result.toString();
    }
}
