package com.example.datingtrap.service.MessageService.Impl;

import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.MessageDTO;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.entity.Matches;
import com.example.datingtrap.entity.Message;
import com.example.datingtrap.entity.User;
import com.example.datingtrap.repository.MatchRepository;
import com.example.datingtrap.repository.MessageRepository;
import com.example.datingtrap.repository.UserRepository;
import com.example.datingtrap.service.MessageService.MessageService;
import com.example.datingtrap.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private NotificationService notificationService;

    public ResponseEntity<Paging<MessageDTO>> findChatBetween(Long matchId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Message> messagePage = messageRepository.findMessagesByMatchId(matchId, pageable);

        List<MessageDTO> dtos = messagePage.getContent()
                .stream()
                .map(MessageDTO::new)
                .collect(Collectors.toList());
        Collections.reverse(dtos);

        Paging<MessageDTO> paging = new Paging<>(
                dtos,
                messagePage.getNumber(),
                messagePage.getTotalPages(),
                messagePage.getTotalElements()
        );

        return ResponseEntity.ok(paging);
    }

    public ResponseEntity<ApiResponse> sendMessage(MessageDTO request) {
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Matches match = matchRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Match not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMatches(match);
        message.setMessage(request.getMessage());
        message.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message);

//        // Send notification to receiver
//        String senderName = sender.getProfile() != null ? sender.getProfile().getFullName() : "Someone";
//        notificationService.createMessageNotification(receiver.getId(), match.getId(), senderName);

        ApiResponse response = new ApiResponse("Message sent successfully", "SUCCESS");
        return ResponseEntity.ok(response);
    }

}
