package com.noxinfinity.pdating.Implementations.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {
    public FirebaseToken getVerifyToken(String token) throws Exception {
        return FirebaseAuth.getInstance().verifyIdToken(token);
    }
    public Boolean isTokenVerified(String token)throws Exception {
        return FirebaseAuth.getInstance().verifyIdToken(token).isEmailVerified();
    }
}
