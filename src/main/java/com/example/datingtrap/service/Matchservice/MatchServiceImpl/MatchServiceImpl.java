package com.example.datingtrap.service.Matchservice.MatchServiceImpl;

import com.example.datingtrap.dto.*;
import com.example.datingtrap.entity.Matches;
import com.example.datingtrap.entity.User;
import com.example.datingtrap.repository.MatchRepository;
import com.example.datingtrap.repository.UserRepository;
import com.example.datingtrap.service.Matchservice.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ApiResponse> createMatch(CreateMatchRequest request){
        User user1 = userRepository.findById(request.getUserIdA()).orElseThrow(() -> new RuntimeException("User A not found with ID: " + request.getUserIdA()));
        User user2 = userRepository.findById(request.getUserIdB()).orElseThrow(() -> new RuntimeException("User B not found with ID: " + request.getUserIdB()));

        Matches matches = new Matches();
        matches.setUserIdA(user1);
        matches.setUserIdB(user2);

        matchRepository.save(matches);
        ApiResponse response = new ApiResponse("Match created successfully", "SUCCESS");
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<Paging<ListMatchConvo>> getMatches(Long userId, int page, int size) {
        int offset = page  * size;

        List<ListMatchConvo> matches = matchRepository.findMatchesByUserPaged(userId, size, offset);

        long totalElements = matchRepository.countMatchesByUser(userId);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        Paging<ListMatchConvo> paging = new Paging<>(
                matches,
                page,
                totalPages,
                totalElements
        );

        return ResponseEntity.ok(paging);
    }




}
