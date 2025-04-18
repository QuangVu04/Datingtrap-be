package com.example.datingtrap.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User userIdA;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User userIdB;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserIdA() {
        return userIdA;
    }

    public void setUserIdA(User userIdA) {
        this.userIdA = userIdA;
    }

    public User getUserIdB() {
        return userIdB;
    }

    public void setUserIdB(User userIdB) {
        this.userIdB = userIdB;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
