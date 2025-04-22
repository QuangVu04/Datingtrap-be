package com.example.datingtrap.entity;

import com.example.datingtrap.enums.SwipeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "swipes")
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "swiper_id", nullable = false)
    private User swiper;

    @ManyToOne
    @JoinColumn(name = "swiped_id", nullable = false)
    private User swiped;

    @Enumerated(EnumType.STRING)
    private SwipeType type;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
