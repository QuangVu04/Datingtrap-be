package com.example.datingtrap.controller;

import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.NotificationDTO;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Paging<NotificationDTO>> getNotifications(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return notificationService.getUserNotifications(userId, page, size);
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam Long userId) {
        return notificationService.getUnreadCount(userId);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse> markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }

    @PutMapping("/read-all")
    public ResponseEntity<ApiResponse> markAllAsRead(@RequestParam Long userId) {
        return notificationService.markAllAsRead(userId);
    }
}
