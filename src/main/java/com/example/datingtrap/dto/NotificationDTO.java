package com.example.datingtrap.dto;

import com.example.datingtrap.entity.Notification;
import com.example.datingtrap.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long userId;
    private NotificationType type;
    private String message;
    private Boolean isRead;
    private Long referenceId; // ID of related entity (match or message)
    private LocalDateTime createdAt;

    // Optional fields for FCM
    private String title;
    private String body;
    private String imageUrl;

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUser().getId();
        this.type = notification.getType();
        this.message = notification.getMessage();
        this.isRead = notification.getIsRead();
        this.referenceId = notification.getReferenceId();
        this.createdAt = notification.getCreatedAt();
    }
}
