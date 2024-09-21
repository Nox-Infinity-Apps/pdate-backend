package com.noxinfinity.pdating.Domains.Storage;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public abstract @Data class UploadResultAbstract {
    String objectId = "";

}
