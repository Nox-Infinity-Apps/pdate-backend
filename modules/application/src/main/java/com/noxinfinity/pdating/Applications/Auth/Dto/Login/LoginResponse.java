package com.noxinfinity.pdating.Applications.Auth.Dto.Login;

import com.noxinfinity.pdating.Applications.Base.BaseResponse;
import org.springframework.http.HttpStatus;

public record LoginResponse(HttpStatus statusHttp, String token) implements BaseResponse {
}
