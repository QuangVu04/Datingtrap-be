package com.example.datingtrap.controller;


import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.MessageDTO;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.service.MessageService.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    public MessageService messageService;

    @GetMapping
    public ResponseEntity<Paging<MessageDTO>> getMessages(
            @RequestParam Long matchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {

        System.out.println(">>> GET MESSAGES called with matchId=" + matchId);
        return messageService.findChatBetween(matchId, page, size);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody MessageDTO request) {
        System.out.println(">>> GET MESSAGES=" + request.getMessage());
        return messageService.sendMessage(request);
    }
}
