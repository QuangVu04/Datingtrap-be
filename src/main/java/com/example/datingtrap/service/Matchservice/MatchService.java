package com.example.datingtrap.service.Matchservice;

import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.CreateMatchRequest;
import com.example.datingtrap.dto.ListMatchConvo;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.entity.Matches;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatchService {
    ResponseEntity<ApiResponse> createMatch(CreateMatchRequest request);

    ResponseEntity<Paging<ListMatchConvo>> getMatches(Long userId, int page, int size);
}
