//package com.example.datingtrap.controller;
//
//import com.example.datingtrap.dto.MessageDTO;
//import com.example.datingtrap.service.MessageService.MessageService;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class MessageSocketController {
//    private final SimpMessagingTemplate messagingTemplate;
//    private final MessageService messageService;
//
//    public MessageSocketController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
//        this.messagingTemplate = messagingTemplate;
//        this.messageService = messageService;
//    }
//
//    @MessageMapping("/chat/send")
//    public void sendMessage(MessageDTO messageDTO) {
//        // Lưu vào DB
//        messageService.sendMessage(messageDTO);
//
//        // Gửi realtime tới 2 người (giả sử sử dụng matchId để định danh)
//        messagingTemplate.convertAndSend("/topic/chat/" + messageDTO.getId(), messageDTO);
//    }
//}
