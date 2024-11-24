package com.noxinfinity.pdating.Applications.Auth;

import com.noxinfinity.pdate.graphql.types.LoginWithGoogle;
import com.noxinfinity.pdating.Domains.AuthManagement.Google.IGoogleService;
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
    public LoginWithGoogle loginWithGoogle(String token) {
        try{
            if(ggService.isValidToken(token)){
                return ggService.getEmailUser(token);
            }
            return "Lỗi validate token";
        } catch (Exception e){
            return "Có lỗi phía server";
        }
    }

    @Override
    public String loginWithApple(String token) {
        return "";
    }
}
