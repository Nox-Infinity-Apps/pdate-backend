package com.noxinfinity.pdating.Domains.AuthManagement.Google;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.noxinfinity.pdating.Implementations.Firebase.FirebaseService;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


class FirebaseInvalidException extends Exception{

}

@Service
public class GoogleService implements IGoogleService{

    private final FirebaseService firebaseService;
    @Autowired
    public GoogleService(FirebaseService firebaseService){
        this.firebaseService = firebaseService;
    }

    @Override
    public Boolean isValidToken(String token)  {
        try {
            firebaseService.getInstant().verifyIdToken(token);
            return true;
        }catch (FirebaseAuthException e) {
            return false;
        }
    }

    @Override
    public void checkToken(String token) throws Exception {
        try{
             firebaseService.getInstant().verifyIdToken(token);
        }catch (Exception e){
            throw new FirebaseInvalidException();
        }
    }


    @Override
    public Object getUserData(String token) {
        return new Object();
    }

    @Override
    public UserFromGoogle getUser(String token) throws Exception {
        FirebaseToken user =  firebaseService.getInstant().verifyIdToken(token);
        return new UserFromGoogle.Builder().
                fcm_id(user.getUid())
                .email(user.getEmail())
                .avatar(user.getPicture())
                .fullName(user.getName())
                .provider("GOOGLE")
                .build();
    }
}
