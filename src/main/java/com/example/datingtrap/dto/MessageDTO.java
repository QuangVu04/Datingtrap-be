package com.example.datingtrap.dto;

import com.example.datingtrap.entity.Message;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long id;
    private String message;
    private Long senderId;
    private Long receiverId;
    private LocalDateTime createdAt;

    public MessageDTO() {
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.message = message.getMessage();
        this.senderId = message.getSender().getId();
        this.receiverId = message.getReceiver().getId();
        this.createdAt = message.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
