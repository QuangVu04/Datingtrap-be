package com.example.datingtrap.service;

import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.NotificationDTO;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.entity.Notification;
import com.example.datingtrap.entity.User;
import com.example.datingtrap.enums.NotificationType;
import com.example.datingtrap.repository.NotificationRepository;
import com.example.datingtrap.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final FirebaseService firebaseService;

    /**
     * Create a new notification and send it via Firebase
     */
    @Transactional
    public Notification createNotification(Long userId, NotificationType type, String message, Long referenceId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .message(message)
                .isRead(false)
                .referenceId(referenceId)
                .build();

        notification = notificationRepository.save(notification);

        // Send push notification
        NotificationDTO notificationDTO = new NotificationDTO(notification);

        // Set title based on notification type
        switch (type) {
            case MATCH:
                notificationDTO.setTitle("New Match!");
                break;
            case MESSAGE:
                notificationDTO.setTitle("New Message");
                break;
            case LIKE:
                notificationDTO.setTitle("Someone Liked You");
                break;
            default:
                notificationDTO.setTitle("Dating Trap");
                break;
        }

        firebaseService.sendNotificationToUser(userId, notificationDTO);

        return notification;
    }

    /**
     * Get user notifications with pagination
     */
    public ResponseEntity<Paging<NotificationDTO>> getUserNotifications(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationPage = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);

        List<NotificationDTO> dtos = notificationPage.getContent().stream()
                .map(NotificationDTO::new)
                .collect(Collectors.toList());

        Paging<NotificationDTO> paging = new Paging<>(
                dtos,
                notificationPage.getNumber(),
                notificationPage.getTotalPages(),
                notificationPage.getTotalElements()
        );

        return ResponseEntity.ok(paging);
    }

    /**
     * Mark notification as read
     */
    @Transactional
    public ResponseEntity<ApiResponse> markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setIsRead(true);
        notificationRepository.save(notification);

        return ResponseEntity.ok(new ApiResponse("Notification marked as read", "SUCCESS"));
    }

    /**
     * Mark all notifications as read for a user
     */
    @Transactional
    public ResponseEntity<ApiResponse> markAllAsRead(Long userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalse(userId);

        unreadNotifications.forEach(notification -> notification.setIsRead(true));
        notificationRepository.saveAll(unreadNotifications);

        return ResponseEntity.ok(new ApiResponse("All notifications marked as read", "SUCCESS"));
    }

    /**
     * Get count of unread notifications
     */
    public ResponseEntity<Long> getUnreadCount(Long userId) {
        Long unreadCount = notificationRepository.countUnreadByUserId(userId);
        return ResponseEntity.ok(unreadCount);
    }

    /**
     * Create match notification
     */
    public void createMatchNotification(Long userId, Long matchId, String matchName) {
        String message = "You matched with " + matchName + "!";
        createNotification(userId, NotificationType.MATCH, message, matchId);
    }

    /**
     * Create message notification
     */
    public void createMessageNotification(Long userId, Long matchId, String senderName) {
        String message = "New message from " + senderName;
        createNotification(userId, NotificationType.MESSAGE, message, matchId);
    }

    /**
     * Create like notification
     */
    public void createLikeNotification(Long userId, Long swiperId) {
        String message = "Someone liked your profile!";
        createNotification(userId, NotificationType.LIKE, message, swiperId);
    }
}
