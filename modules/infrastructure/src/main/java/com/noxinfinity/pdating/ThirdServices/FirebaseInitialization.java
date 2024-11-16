package com.noxinfinity.pdating.ThirdServices;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.util.List;

@Configuration
public class FirebaseInitialization {

    @Bean
    public FirebaseApp initialization() {
        try {

            List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
            if (!firebaseApps.isEmpty()) {
                return firebaseApps.get(0);
            }

            FileInputStream serviceAccount =
                    new FileInputStream("/Users/n0x/IDEAIJ/GCGV/modules/app/src/main/resources/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            return FirebaseApp.initializeApp(options);
        } catch (Exception error) {
            error.printStackTrace();
            throw new RuntimeException("Failed to initialize Firebase", error);
        }
    }
}
