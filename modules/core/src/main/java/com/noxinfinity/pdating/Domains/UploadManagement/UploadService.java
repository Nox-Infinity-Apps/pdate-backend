package com.noxinfinity.pdating.Domains.UploadManagement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.noxinfinity.pdating.graphql.types.CloudinaryUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadService implements IUploadService {
    private final Cloudinary cloudinary;
    @Autowired
    public UploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryUploadResult uploadImage(MultipartFile file) throws IOException {
        try {
            // Upload file vào thư mục "P_Date"
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "auto",
                            "folder", "P_Date"
                    ));

            CloudinaryUploadResult result = new CloudinaryUploadResult();
            result.setUrl(uploadResult.get("url") != null ? uploadResult.get("url").toString() : null);
            result.setSecureUrl(uploadResult.get("secure_url") != null ? uploadResult.get("secure_url").toString() : null);
            result.setPublicId(uploadResult.get("public_id") != null ? uploadResult.get("public_id").toString() : null);
            result.setResourceType(uploadResult.get("resource_type") != null ? uploadResult.get("resource_type").toString() : null);
            result.setCreatedAt(uploadResult.get("created_at") != null ? uploadResult.get("created_at").toString() : null);
            result.setFolder(uploadResult.get("folder") != null ? uploadResult.get("folder").toString() : null);
            result.setType(uploadResult.get("type") != null ? uploadResult.get("type").toString() : null);
            result.setOriginalFilename(uploadResult.get("original_filename") != null ? uploadResult.get("original_filename").toString() : null);
            result.setSignature(uploadResult.get("signature") != null ? uploadResult.get("signature").toString() : null);

            return result;
        } catch (IOException e) {
            throw new IOException("Failed to upload image to Cloudinary", e);
        }
    }

    public boolean deleteImage(String publicId) throws IOException {
        try {
            Map<?, ?> deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            Object result = deleteResult.get("result");
            return result != null && "ok".equals(result.toString());
        } catch (IOException e) {
            throw new IOException("Failed to delete image from Cloudinary", e);
        }
    }

    public CloudinaryUploadResult updateImage(MultipartFile newFile, String oldPublicId) throws IOException {
        // Upload ảnh mới
        CloudinaryUploadResult newImageResult = uploadImage(newFile);
        // Xóa ảnh cũ nếu tồn tại
        if (oldPublicId != null && !oldPublicId.isEmpty()) {
            deleteImage(oldPublicId);
        }

        return newImageResult;
    }
}
