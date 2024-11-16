package com.noxinfinity.pdating.Domains.AuthManagement.Google;
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
}
