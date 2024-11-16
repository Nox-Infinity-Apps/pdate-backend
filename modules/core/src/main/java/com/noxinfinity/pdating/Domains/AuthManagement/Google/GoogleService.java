package com.noxinfinity.pdating.Domains.AuthManagement.Google;
import com.noxinfinity.pdate.graphql.types.LoginWithGoogle;
import com.noxinfinity.pdate.graphql.types.UserFromGoogle;
import com.noxinfinity.pdating.Implementations.Firebase.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleService implements IGoogleService{

    private final FirebaseService firebaseService;
    @Autowired
    public GoogleService(FirebaseService firebaseService){
        this.firebaseService = firebaseService;
    }

    @Override
    public Boolean isValidToken(String token) throws Exception {
        return firebaseService.isTokenVerified(token);
    }

    @Override
    public Object getUserData(String token) {
        return new Object();
    }

    @Override
    public String getEmailUser(String token) throws Exception {
        return firebaseService.getEmailTokenVerifired(token);
    }

    @Override
    public LoginWithGoogle getUserGoogleByToken(String token) throws Exception {
        UserFromGoogle user = firebaseService.getUidTokenVerifired(token);
        return new LoginWithGoogle.Builder().user(user).accessToken(token).build();
    }
}
