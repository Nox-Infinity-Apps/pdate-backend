package com.noxinfinity.pdating.Applications.Auth.Dto.ChangePassword;

import com.noxinfinity.pdating.Applications.Base.BaseResponse;
import org.springframework.http.HttpStatus;

public record ChangePasswordResponse(HttpStatus statusHttp,String data) implements BaseResponse {
}
