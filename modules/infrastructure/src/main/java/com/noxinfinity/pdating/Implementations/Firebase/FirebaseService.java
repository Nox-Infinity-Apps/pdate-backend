package com.noxinfinity.pdating.Implementations.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FirebaseService {
    public FirebaseAuth getInstant() {
        return FirebaseAuth.getInstance();
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
