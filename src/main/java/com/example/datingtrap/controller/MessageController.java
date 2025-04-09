package com.example.datingtrap.controller;


import com.example.datingtrap.dto.MessageDTO;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.entity.Message;
import com.example.datingtrap.service.MessageService.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    public MessageService messageService;

    @GetMapping
    public  ResponseEntity<Paging<MessageDTO>> getMessages(
            @RequestParam Long matchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {

        System.out.println(">>> GET MESSAGES called with matchId=" + matchId);
        return messageService.findChatBetween(matchId, page, size);
    }
}
