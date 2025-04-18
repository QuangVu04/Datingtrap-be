package com.example.datingtrap.dto;

import lombok.*;
import com.example.datingtrap.entity.Preference;
import com.example.datingtrap.entity.Hobby;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String fullName;
    private Integer age;
    private String bio;
    private String gender;
    private String job;
    private String location;
    private String avatarUrl;
    private LocalDate birthDate;
    private PreferenceDTO preference;
    private Set<Long> hobbyIds;

}
