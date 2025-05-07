package com.example.datingtrap.controller;

import com.example.datingtrap.dto.ListMatchConvo;
import com.example.datingtrap.dto.MessageDTO;
import com.example.datingtrap.service.MessageService.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages") // Clients must subscribe to this topic to receive messages
    public MessageDTO sendMessage(@Payload MessageDTO messageDTO) {
        // Optionally save the message in DB
        messageService.sendMessage(messageDTO);
        ListMatchConvo updatedConvo = messageService.getUpdateConvo(messageDTO.getId(), messageDTO.getSenderId());

        messagingTemplate.convertAndSend("/topic/match.update." + messageDTO.getSenderId(), updatedConvo);
        messagingTemplate.convertAndSend("/topic/match.update." + messageDTO.getReceiverId(), updatedConvo);
        return messageDTO;
    }


}