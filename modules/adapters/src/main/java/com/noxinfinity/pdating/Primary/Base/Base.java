package com.noxinfinity.pdating.Primary.Base;

import com.noxinfinity.pdating.Applications.Base.BaseResponse;
import com.noxinfinity.pdating.GraphQL.Exception.UnauthorizedException;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    public static String getUserId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
        return decodedToken.getFcm_id();
    }


}

