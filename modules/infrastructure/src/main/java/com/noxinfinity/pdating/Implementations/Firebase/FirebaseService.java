package com.noxinfinity.pdating.Implementations.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {
    public FirebaseAuth getInstant() {
        return FirebaseAuth.getInstance();
    }
}
