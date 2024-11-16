package com.noxinfinity.pdating.Applications.Auth;

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
    public String loginWithGoogle(String token) {
        try{
            if(ggService.isValidToken(token)){
                return ggService.getUserData(token).toString();
            }
            return "";
        } catch (Exception e){
            return "";
        }
    }

    @Override
    public String loginWithApple(String token) {
        return "";
    }
}
