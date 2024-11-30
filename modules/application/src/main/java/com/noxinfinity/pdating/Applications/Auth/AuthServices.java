package com.noxinfinity.pdating.Applications.Auth;

import com.noxinfinity.pdating.Domains.AuthManagement.Google.IGoogleService;
import com.noxinfinity.pdating.Domains.AuthManagement.JWT.IJwtService;
import com.noxinfinity.pdating.Domains.MailManagement.EmailService;
import com.noxinfinity.pdating.Domains.UserDataManagement.UserData.IUserDataService;
import com.noxinfinity.pdating.graphql.types.LoginWithGoogle;
import com.noxinfinity.pdating.graphql.types.UserData;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServices implements IAuth{
    private IGoogleService ggService;
    private IJwtService jwtService;
    private IUserDataService userDataService;
    private EmailService emailService;
    @Autowired
    public AuthServices(IGoogleService ggService , IJwtService jwtService, IUserDataService userDataService, EmailService emailService){
        this.ggService = ggService;
        this.jwtService = jwtService;
        this.userDataService = userDataService;
        this.emailService = emailService;
    }
    @Override
    public LoginWithGoogle loginWithGoogle(String token) throws Exception {
            if(ggService.isValidToken(token)){
                UserFromGoogle user =  ggService.getUser(token);
                //Tao token
                String accessToken = jwtService.generateToken(user);
                Boolean isNew = userDataService.createOrUpdateUserDataFromGoogleReturnIsNew(user);
                UserData userData = userDataService.getUserDataById(user.getFcm_id());
                if(userData != null){
                    user.setAvatar(userData.getAvatar());
                }
                if(isNew){
                    emailService.sendEmail(user.getEmail(), "Welcome to PDATE", EmailService.readHtmlFile("modules/adapters/src/main/resources/templates/send_mail_template.html"));
                }
                return new LoginWithGoogle.Builder().user(user).accessToken(accessToken).isNew(isNew).build();
            }
           throw new Exception("Access token is not valid");
    }

    @Override
    public String loginWithApple(String token) {
        return "";
    }
}
