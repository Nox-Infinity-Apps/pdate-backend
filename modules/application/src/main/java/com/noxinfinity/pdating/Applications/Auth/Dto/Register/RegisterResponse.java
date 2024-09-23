package com.noxinfinity.pdating.Applications.Auth.Dto.Register;

import com.noxinfinity.pdating.Applications.Base.BaseResponse;
import org.springframework.http.HttpStatus;

public record RegisterResponse(HttpStatus statusHttp,String data) implements BaseResponse {
}
