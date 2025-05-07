package com.example.datingtrap.service.MessageService;

import com.example.datingtrap.dto.ApiResponse;
import com.example.datingtrap.dto.ListMatchConvo;
import com.example.datingtrap.dto.MessageDTO;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.entity.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageService {

    ResponseEntity<Paging<MessageDTO>> findChatBetween(Long matchId, int page, int size);

    ResponseEntity<ApiResponse> sendMessage(MessageDTO request);

    ListMatchConvo getUpdateConvo(Long matchId);
}
