package com.example.datingtrap.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
public class Profiles {

    @Id
    @Column(name = "user_id")
    private long userId;

    @OneToOne
    @MapsId  // dùng khi khóa chính cũng là khóa ngoại
    @JoinColumn(name = "user_id")
    private User user;

    private String fullName;
    private Integer age;
    private String bio;
    private String gender;
    private String job;
    private String location;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}
