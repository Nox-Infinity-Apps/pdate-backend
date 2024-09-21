package com.noxinfinity.pdating.Implementations.Minio;

import com.noxinfinity.pdating.Domains.Storage.StorageServiceInterface;
import com.noxinfinity.pdating.Domains.Storage.UploadResultAbstract;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioService implements StorageServiceInterface {
    private final MinioClient minioClient;
    private final String bucketName;


    @Autowired
    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
        //! TODO: Fix cung truoc
        this.bucketName = "pdate-profile-pic";
    }

    @Override
    public UploadResultAbstract uploadFile(InputStream inputStream, String contentType, String objectName) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            ObjectWriteResponse response = minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());
            return UploadResult.builder().build().setObjectId(response.object());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public void getUrl() {

    }
}
