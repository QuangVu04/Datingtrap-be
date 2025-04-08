package com.example.datingtrap.controller;


import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.CreateMatchRequest;
import com.example.datingtrap.dto.ListMatchConvo;
import com.example.datingtrap.entity.Matches;
import com.example.datingtrap.service.Matchservice.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping
    public ResponseEntity<ApiResponse> createMatch(@RequestBody CreateMatchRequest request){
        return matchService.createMatch(request);
    }

    @GetMapping
    public ResponseEntity<List<ListMatchConvo>> getMatches(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return matchService.getMatches(userId, page, size);
    }
}
