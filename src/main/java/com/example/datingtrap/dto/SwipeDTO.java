package com.example.datingtrap.dto;

import com.example.datingtrap.enums.SwipeType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SwipeDTO {
    private Long swiperId;
    private Long swipedId;
    private SwipeType type;
}
