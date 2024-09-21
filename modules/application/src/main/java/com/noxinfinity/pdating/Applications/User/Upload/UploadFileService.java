package com.noxinfinity.pdating.Applications.User.Upload;

import com.noxinfinity.pdating.Domains.Storage.StorageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadFileService {
    private final StorageServiceInterface storageService;

    @Autowired
    public UploadFileService(StorageServiceInterface storageService){
        this.storageService = storageService;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        return this.storageService.uploadFile(file.getInputStream(),file.getContentType(),file.getName()).getObjectId();
    }

}
