package com.example.datingtrap.controller;

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
        return messageDTO;
    }


//    @MessageMapping("/chat/send")
//    public void sendMessage(MessageDTO messageDTO) {
//        // Lưu vào DB
//        messageService.sendMessage(messageDTO);
//
//        // Gửi realtime tới 2 người (giả sử sử dụng matchId để định danh)
//        messagingTemplate.convertAndSend("/topic/chat/" + messageDTO.getId(), messageDTO);
//    }
}
