package com.example.datingtrap.service;

import com.example.datingtrap.entity.Profiles;
import com.example.datingtrap.entity.User;
import com.example.datingtrap.repository.ProfileRepository;
import com.example.datingtrap.repository.UserRepository;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    // Constructor injection
    @Autowired
    public AuthService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public ResponseEntity<?> verifyToken (String token){
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String fireBaseid = decodedToken.getUid();

            User user = userRepository.findByFireBase(fireBaseid).orElseGet(() -> {
                User newUser = new User();
                newUser.setFireBase(fireBaseid);
                userRepository.save(newUser);

                // Tạo profile mặc định
                Profiles profile = new Profiles();
                profile.setUser(newUser);
                profile.setUserId(newUser.getId());
                profile.setFullName("New User");
                profile.setAge(18);
                profile.setBio("");
                profile.setGender("unknown");
                profile.setJob("");
                profile.setLocation("");
                profile.setAvatarUrl("");
                profile.setBirthDate(null);

                profileRepository.save(profile);
                return newUser;
            });
            return ResponseEntity.ok(Collections.singletonMap("userId", user.getId()));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid token"));
        }
    }
}

