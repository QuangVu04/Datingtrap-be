package com.example.datingtrap.service.SwipeService;

import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.dto.SwipeDTO;
import com.example.datingtrap.dto.UserSwipeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SwipeService {

    ApiResponse swipe(SwipeDTO dto);

    Page<UserSwipeDTO> getSwipeRecommendations(Long userId, int page, int size);
}
