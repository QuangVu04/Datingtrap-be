package com.example.datingtrap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSwipeDTO {
    private Long id;
    private String fullName;
    private Integer age;
    private String bio;
    private String gender;
    private String job;
    private String location;
    private String avatarUrl;
    private List<String> hobbies;
    private Integer hobbyMatchCount;
}
