//package com.noxinfinity.pdating.Primary.Auth;
//
//import com.netflix.graphql.dgs.DgsComponent;
//import com.netflix.graphql.dgs.DgsMutation;
//import com.netflix.graphql.dgs.InputArgument;
//import com.netflix.graphql.dgs.context.DgsContext;
//import com.noxinfinity.pdating.Applications.Auth.Dto.ChangePassword.ChangePasswordRequest;
//import com.noxinfinity.pdating.Applications.Auth.Dto.ChangePassword.ChangePasswordResponse;
//import com.noxinfinity.pdating.Applications.Auth.Dto.ChangePassword.ChangePasswordResponseData;
//import com.noxinfinity.pdating.Applications.Auth.Dto.Login.LoginRequest;
//import com.noxinfinity.pdating.Applications.Auth.Dto.Login.LoginResponse;
//import com.noxinfinity.pdating.Applications.Auth.Dto.Login.LoginResponseData;
//import com.noxinfinity.pdating.Applications.Auth.Dto.Register.RegisterRequest;
//import com.noxinfinity.pdating.Applications.Auth.Dto.Register.RegisterResponse;
//import com.noxinfinity.pdating.Applications.Auth.Dto.Register.RegisterResponseData;
//import com.noxinfinity.pdating.Primary.Base.Base;
//import com.noxinfinity.pdating.Primary.Base.Response;
//import graphql.schema.DataFetchingEnvironment;
//import org.springframework.beans.factory.annotation.Autowired;
//import jakarta.servlet.http.HttpServletRequest;
//
//@DgsComponent
//public class AuthMutation {
//    private final AuthApplicationService authApplicationService;
//    @Autowired
//    public AuthMutation(AuthApplicationService authApplicationService) {
//        this.authApplicationService = authApplicationService;
//    }
//
//    @DgsMutation
//    public Response<Object> login(@InputArgument LoginRequest loginRequest) {
//        LoginResponse response = authApplicationService.login(loginRequest);
//        return Base.toResponse(response,new LoginResponseData(response));
//    }
//
//    @DgsMutation
//    public Response<Object> register(@InputArgument RegisterRequest registerRequest) {
//        RegisterResponse response = authApplicationService.register(registerRequest);
//        return Base.toResponse(response,new RegisterResponseData(response));
//    }
//
//    @DgsMutation
//    public Response<Object> changePassword(@InputArgument ChangePasswordRequest changePasswordRequest, DataFetchingEnvironment dfe) {
//        ChangePasswordResponse response = authApplicationService.changePassword(changePasswordRequest, dfe);
//        return Base.toResponse(response,new ChangePasswordResponseData(response));
//    }
//}
