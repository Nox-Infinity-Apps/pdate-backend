package com.noxinfinity.pdating.Domains.Storage;

import java.io.InputStream;

public interface StorageServiceInterface {
    UploadResultAbstract uploadFile(InputStream inputStream, String contentType, String objectName);
    void getUrl();
}

