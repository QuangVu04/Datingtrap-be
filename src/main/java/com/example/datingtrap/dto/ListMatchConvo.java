package com.example.datingtrap.dto;

import java.time.LocalDateTime;

public interface ListMatchConvo {
    Long getMatchId();
    Long getPartnerId();
    String getPartnerUsername();
    String getPartnerAvatarUrl();
    String getLastMessage();
    LocalDateTime getLastMessageTime();
}
