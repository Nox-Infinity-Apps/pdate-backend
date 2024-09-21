package com.noxinfinity.pdating.Implementations.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.noxinfinity.pdating.Domains.Cloudinary.CloudinaryInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService implements CloudinaryInterface {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public Map<?,?> upload(MultipartFile file, String folderName) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folderName
                ));
    }

    @Override
    public Map<?, ?> download(MultipartFile file, String folderName) {
        return Map.of();
    }

}
