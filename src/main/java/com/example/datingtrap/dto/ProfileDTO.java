package com.example.datingtrap.dto;

import com.example.datingtrap.entity.Profile;
import com.example.datingtrap.entity.User;
import com.example.datingtrap.entity.Preference;
import com.example.datingtrap.entity.Hobby;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.*;

import java.time.LocalDate;

@Data
public class ProfileDTO {
    private Long userId;
    private String username;
    private Integer age;
    private String bio;
    private String gender;
    private String job;
    private String location;
    private String avatarUrl;
    private LocalDate birthDate;
//    private Preference preference;
    private Set<HobbyDTO> hobbies;
    private PreferenceDTO preference;

    public ProfileDTO (Profile profile) {
        this.userId = profile.getUserId();
        this.username = profile.getUser().getUsername();
        this.age = profile.getAge();
        this.gender = profile.getGender();
        this.job = profile.getJob();
        this.location = profile.getLocation();
        this.avatarUrl = profile.getAvatarUrl();
        this.birthDate = profile.getBirthDate();
        this.bio = profile.getBio();
        this.preference = new PreferenceDTO(profile.getUser().getPreference());
        this.hobbies = profile.getUser().getHobbies()
                .stream()
                .map(HobbyDTO::new)
                .collect(Collectors.toSet());
    }
}
