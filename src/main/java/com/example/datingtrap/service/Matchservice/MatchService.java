package com.example.datingtrap.service.Matchservice;

import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.CreateMatchRequest;
import com.example.datingtrap.entity.Matches;
import org.springframework.http.ResponseEntity;

public interface MatchService {
    ResponseEntity<ApiResponse> createMatch(CreateMatchRequest request);
}
