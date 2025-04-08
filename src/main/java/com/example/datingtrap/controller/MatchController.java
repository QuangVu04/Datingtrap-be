package com.example.datingtrap.controller;


import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.CreateMatchRequest;
import com.example.datingtrap.entity.Matches;
import com.example.datingtrap.service.Matchservice.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping
    public ResponseEntity<ApiResponse> createMatch(@RequestBody CreateMatchRequest request){
        return matchService.createMatch(request);
    }
}
