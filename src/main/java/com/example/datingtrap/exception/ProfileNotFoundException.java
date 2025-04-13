package com.example.datingtrap.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Long userId) {
        super("Profile not found for user ID: " + userId);
    }
}