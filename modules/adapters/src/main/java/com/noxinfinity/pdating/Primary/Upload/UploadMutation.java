package com.noxinfinity.pdating.Primary.Upload;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.noxinfinity.pdating.Applications.User.Upload.UploadImageService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@DgsComponent
public class UploadMutation {

    private final UploadImageService uploadImageService;

    @Autowired
    public UploadMutation(UploadImageService uploadImageService){
        this.uploadImageService = uploadImageService;
    }

    @DgsData(parentType = "Mutation", field = "uploadScriptWithMultipartPOST")
    public String uploadScript(DataFetchingEnvironment dfe) throws IOException {
        MultipartFile file = dfe.getArgument("input");
        try{
            return uploadImageService.upload(file);
        }catch (IOException e){
            return "Failed to upload";
        }
    }
}
