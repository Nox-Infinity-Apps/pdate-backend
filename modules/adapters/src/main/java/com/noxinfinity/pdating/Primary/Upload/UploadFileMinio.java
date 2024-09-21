package com.noxinfinity.pdating.Primary.Upload;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.noxinfinity.pdating.Applications.User.Upload.UploadFileService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@DgsComponent
public class UploadFileMinio {
    final UploadFileService uploadFileService;

    @Autowired
    public UploadFileMinio(UploadFileService storageService){
        this.uploadFileService = storageService;
    }

    @DgsData(parentType = "Mutation", field = "uploadFileScriptWithMultipartPOST")
    public String uploadFileScript(DataFetchingEnvironment dfe) throws IOException {
        MultipartFile file = dfe.getArgument("input");
        try{
            assert file != null;
            return uploadFileService.uploadFile(file);
        }catch (IOException e){
            return "Failed to upload";
        }
    }
}
