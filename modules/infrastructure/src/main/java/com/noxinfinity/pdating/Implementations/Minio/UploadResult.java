package com.noxinfinity.pdating.Implementations.Minio;

import com.noxinfinity.pdating.Domains.Storage.UploadResultAbstract;
import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@Accessors(chain = true)
public @Data class UploadResult extends UploadResultAbstract {
    public UploadResult(){

    }
}
