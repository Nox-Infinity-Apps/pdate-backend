package com.noxinfinity.pdating.Applications.User.Upload;

import com.noxinfinity.pdating.Domains.Storage.UploadResultAbstract;
import com.noxinfinity.pdating.Implementations.Minio.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadFileService {
    private final MinioService minioService;

    @Autowired
    public UploadFileService(MinioService minioService){
        this.minioService = minioService;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        return this.minioService.uploadFile(file.getInputStream(),file.getContentType(),file.getName()).getObjectId();
    }

}
