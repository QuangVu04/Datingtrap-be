package com.example.datingtrap.controller;

import com.example.datingtrap.dto.ProfileDTO;
import com.example.datingtrap.dto.RegisterRequestDTO;
import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.service.AuthService;
import com.example.datingtrap.service.ProfileService;
import com.google.api.client.auth.oauth2.TokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ProfileService profileService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO dto) {
        try {
            ProfileDTO profileDTO = profileService.registerNewUserWithProfile(dto);
            return ResponseEntity.ok(profileDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(new ApiResponse("Lỗi khi tạo người dùng mới: " + e.getMessage(), "ERROR"));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        System.out.print(token);
        return authService.verifyToken(token);
    }



}
