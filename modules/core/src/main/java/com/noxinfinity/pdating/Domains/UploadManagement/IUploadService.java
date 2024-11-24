package com.noxinfinity.pdating.Domains.UploadManagement;

import com.noxinfinity.pdating.graphql.types.CloudinaryUploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadService {
    CloudinaryUploadResult uploadImage(MultipartFile file) throws IOException;

    boolean deleteImage(String publicId) throws IOException;

    CloudinaryUploadResult updateImage(MultipartFile newFile, String oldPublicId) throws IOException;
}
