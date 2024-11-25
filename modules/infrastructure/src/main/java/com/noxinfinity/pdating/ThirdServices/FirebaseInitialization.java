package com.noxinfinity.pdating.ThirdServices;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
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

            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");
            if (serviceAccount == null) {
                throw new RuntimeException("Failed to load serviceAccountKey.json");
            }

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
