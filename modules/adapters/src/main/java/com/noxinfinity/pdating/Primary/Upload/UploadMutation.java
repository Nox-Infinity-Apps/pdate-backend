package com.noxinfinity.pdating.Primary.Upload;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@DgsComponent
public class UploadMutation {
    @DgsData(parentType = "Mutation", field = "uploadScriptWithMultipartPOST")
    public boolean uploadScript(DataFetchingEnvironment dfe) throws IOException {
        MultipartFile file = dfe.getArgument("input");
        String content = new String(file.getBytes());
        return ! content.isEmpty();
    }
}
