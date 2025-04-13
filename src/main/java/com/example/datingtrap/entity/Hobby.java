package com.example.datingtrap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hobby")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hobby {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;
}
