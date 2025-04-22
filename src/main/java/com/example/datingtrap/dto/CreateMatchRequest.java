package com.example.datingtrap.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMatchRequest {
    private Long userIdA;
    private Long userIdB;

    public Long getUserIdA() {
        return userIdA;
    }

    public void setUserIdA(Long userIdA) {
        this.userIdA = userIdA;
    }

    public Long getUserIdB() {
        return userIdB;
    }

    public void setUserIdB(Long userIdB) {
        this.userIdB = userIdB;
    }
}
