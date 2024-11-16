package com.noxinfinity.pdating.Implementations.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FirebaseService {
    public FirebaseToken getVerifyToken(String token) throws Exception {
        return FirebaseAuth.getInstance().verifyIdToken(token);
    }
    public Boolean isTokenVerified(String token)throws Exception {
        return FirebaseAuth.getInstance().verifyIdToken(token).isEmailVerified();
    }
    public String getEmailTokenVerifired(String token) throws Exception {
        return FirebaseAuth.getInstance().verifyIdToken(token).getEmail();
    }
    public com.noxinfinity.pdate.graphql.types.UserFromGoogle getUidTokenVerifired(String token) throws Exception {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
        return new com.noxinfinity.pdate.graphql.types.UserFromGoogle.Builder()
                .fcm_id(firebaseToken.getUid()
                .fullName(firebaseToken.getName())
                .avatar(firebaseToken.getPicture())
                .provider("GOOGLE")
                .build();
    }
}
