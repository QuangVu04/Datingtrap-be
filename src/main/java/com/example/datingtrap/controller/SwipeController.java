package com.example.datingtrap.controller;

import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.SwipeDTO;
import com.example.datingtrap.dto.UserSwipeDTO;
import com.example.datingtrap.service.SwipeService.SwipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/swipes")
public class SwipeController {

    @Autowired
    private SwipeService swipeService;

    @PostMapping
    public ResponseEntity<ApiResponse> swipe(@RequestBody SwipeDTO dto) {
        return ResponseEntity.ok(swipeService.swipe(dto));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<Page<UserSwipeDTO>> getSwipeRecommendations(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<UserSwipeDTO> recommendations =
               swipeService.getSwipeRecommendations(userId, page, size);
        return ResponseEntity.ok(recommendations);
    }


}
