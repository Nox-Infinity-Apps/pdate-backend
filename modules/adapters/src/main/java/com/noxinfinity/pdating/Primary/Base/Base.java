package com.noxinfinity.pdating.Primary.Base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.context.DgsContext;
import com.noxinfinity.pdating.Applications.Base.BaseResponse;
import com.noxinfinity.pdating.GraphQL.Exception.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

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
    public static String extractTokenFromDfe(DgsDataFetchingEnvironment dfe) throws UnauthorizedException {
        HttpHeaders headers = DgsContext.getRequestData(dfe).getHeaders();

        if (headers == null) {
            throw new UnauthorizedException("Request context is missing");
        }

        String authHeader = headers.getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Unauthorized: Missing or invalid Authorization header");
        }

        return authHeader.substring(7);
    }
}

