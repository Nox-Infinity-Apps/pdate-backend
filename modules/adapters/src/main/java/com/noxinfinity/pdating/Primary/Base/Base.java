package com.noxinfinity.pdating.Primary.Base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noxinfinity.pdating.Applications.Base.BaseResponse;
import org.springframework.http.HttpStatus;

public class Base {
    public static Response<Object> toResponse(BaseResponse baseResponse, Object data) {
        return new Response<>(baseResponse.statusHttp().value(),
                baseResponse.statusHttp().getReasonPhrase(),
                data,
                msg(baseResponse));
    }
    public static String msg(BaseResponse baseResponse) {
        if (baseResponse == null) {
            return null;
        }
        if (baseResponse.statusHttp() == HttpStatus.OK ||
        baseResponse.statusHttp() == HttpStatus.CREATED ||
        baseResponse.statusHttp() == HttpStatus.ACCEPTED) {
            return "success";
        }
        if (baseResponse.statusHttp() == HttpStatus.INTERNAL_SERVER_ERROR) {
            return "internal";
        }
        if (baseResponse.statusHttp() == HttpStatus.NOT_FOUND) {
            return "not found";
        }
        return "error";
    }
}

