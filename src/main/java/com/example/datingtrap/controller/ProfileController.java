package com.example.datingtrap.controller;

import com.example.datingtrap.dto.ProfileDTO;
import com.example.datingtrap.dto.ProfileUpdateDTO;
import com.example.datingtrap.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{userId}")
    public ProfileDTO getProfile(@PathVariable Long userId) {
        return profileService.getProfileByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> updateProfile(
            @PathVariable Long id,
            @RequestBody ProfileUpdateDTO dto
    ) {
        return ResponseEntity.ok(profileService.updateProfile(id, dto));
    }
}
