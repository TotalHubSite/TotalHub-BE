package com.totalhubsite.backend.domain.chat.dto.request;

import lombok.Builder;

public record ChatMessageRequestDto(
    String roomId,
    String sender,
    String content
) {

    @Builder
    public ChatMessageRequestDto(String roomId, String sender, String content) {
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
    }
}
