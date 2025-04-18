package com.example.datingtrap.controller;

import com.example.datingtrap.dto.ProfileDTO;
import com.example.datingtrap.dto.RegisterRequestDTO;
import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ProfileService profileService;

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
}
