package com.example.datingtrap.service.MessageService.Impl;

import com.example.datingtrap.dto.MessageDTO;
import com.example.datingtrap.dto.Paging;
import com.example.datingtrap.entity.Message;
import com.example.datingtrap.repository.MessageRepository;
import com.example.datingtrap.service.Matchservice.MatchService;
import com.example.datingtrap.service.MessageService.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

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

}
