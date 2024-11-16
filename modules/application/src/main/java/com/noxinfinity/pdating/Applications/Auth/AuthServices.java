package com.noxinfinity.pdating.Applications.Auth;

import com.noxinfinity.pdating.Domains.AuthManagement.Google.IGoogleService;
import com.noxinfinity.pdating.graphql.types.LoginWithGoogle;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServices implements IAuth{
    private IGoogleService ggService;
    @Autowired
    public AuthServices(IGoogleService ggService){
        this.ggService = ggService;
    }
    @Override
    public LoginWithGoogle loginWithGoogle(String token) throws Exception {
            if(ggService.isValidToken(token)){
                UserFromGoogle user =  ggService.getUser(token);
                return new LoginWithGoogle.Builder().user(user).accessToken(token).build();
            }
           throw new Exception("Access token is not valid");
    }

    @Override
    public String loginWithApple(String token) {
        return "";
    }
}
