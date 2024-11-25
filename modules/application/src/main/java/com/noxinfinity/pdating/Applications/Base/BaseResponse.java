package com.noxinfinity.pdating.Applications.Base;

import com.noxinfinity.pdating.graphql.types.UserData;
import org.springframework.http.HttpStatus;

public interface BaseResponse {
    HttpStatus statusHttp();
}

