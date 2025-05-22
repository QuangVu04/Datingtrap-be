package com.example.datingtrap.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;


@Configuration
public class FirebaseConfig {

        @PostConstruct
        public void init() {
            try {
                FileInputStream serviceAccount =
                        new FileInputStream("src/main/resources/tinder-clone-b63c7-firebase-adminsdk-fbsvc-30f69e05d6.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }
                System.out.print("firebase ok");
            } catch (IOException e) {
                throw new RuntimeException("Không thể khởi tạo Firebase", e);
            }
        }
    }


