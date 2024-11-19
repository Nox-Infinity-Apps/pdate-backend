package com.noxinfinity.pdating.Primary.Auth;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.graphql.types.LoginWithGoogle;
import com.netflix.graphql.dgs.InputArgument;
import com.noxinfinity.pdating.graphql.types.LoginByGoogleResponse;
import com.noxinfinity.pdating.graphql.types.StatusEnum;
import com.noxinfinity.pdating.Applications.Auth.IAuth;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@DgsComponent
public class AuthMutation {
    private final IAuth authService;
    @Autowired
    public AuthMutation(IAuth authService){
        this.authService = authService;
    }

    @DgsMutation()
    public LoginByGoogleResponse loginByGoogle(@InputArgument(name = "token") String token) throws Exception {
        try {
            LoginWithGoogle response = authService.loginWithGoogle(token);
            return new LoginByGoogleResponse
                    .Builder()
                    .message("Success")
                    .status(StatusEnum.SUCCESS)
                    .accessToken(response.getAccessToken())
                    .user(response.getUser())
                    .isNew(response.getIsNew())
                    .build();
        }catch (Exception e){
            return new LoginByGoogleResponse
                    .Builder()
                    .message(e.getMessage())
                    .status(StatusEnum.FAILED)
                    .accessToken("")
                    .isNew(false)
                    .build();
        }
    }

    @DgsMutation()
    @ValidateToken
    public String loginByApple(@InputArgument(name = "token") String token) {
        try{
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            UserFromGoogle decodedToken = (UserFromGoogle) request.getAttribute("decodedToken");
            return decodedToken.getEmail();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
