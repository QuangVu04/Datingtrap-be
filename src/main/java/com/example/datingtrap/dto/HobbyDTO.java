package com.example.datingtrap.dto;

import lombok.*;
import com.example.datingtrap.entity.Hobby;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HobbyDTO {
    private Integer id;
    private String name;

    public HobbyDTO(Hobby hobby) {
        this.id = hobby.getId();
        this.name = hobby.getName();
    }
}
