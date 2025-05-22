package com.example.datingtrap.service;

import com.example.datingtrap.dto.NotificationDTO;
import com.example.datingtrap.entity.User;
import com.example.datingtrap.repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseService {
    private final UserRepository userRepository;

    public void sendNotification(NotificationDTO notification, String fcmToken) {
        try {
            if (fcmToken == null) {
                log.warn("Cannot send notification - Firebase ID is null");
                return;
            }

            // Build notification
            Notification fcmNotification = Notification.builder()
                    .setTitle(notification.getTitle())
                    .setBody(notification.getMessage())
                    .build();

            // Create data payload
            Map<String, String> data = new HashMap<>();
            data.put("notificationId", notification.getId().toString());
            data.put("type", notification.getType().toString());
            data.put("referenceId", notification.getReferenceId() != null
                    ? notification.getReferenceId().toString() : "");

            // Create message
            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(fcmNotification)
                    .putAllData(data)
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .build())
                    .setApnsConfig(ApnsConfig.builder()
                            .putHeader("apns-priority", "10")
                            .setAps(Aps.builder()
                                    .setSound("default")
                                    .setBadge(1)
                                    .setContentAvailable(true)
                                    .build())
                            .build())
                    .build();

            // Send message
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent notification: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("Error sending notification: {}", e.getMessage());
        }
    }

    public void sendNotificationToUser(Long userId, NotificationDTO notification) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent() && userOpt.get().getFcmToken() != null) {
            sendNotification(notification, userOpt.get().getFcmToken());
        } else {
            log.warn("User with ID {} not found or has no Firebase ID", userId);
        }
    }
}
