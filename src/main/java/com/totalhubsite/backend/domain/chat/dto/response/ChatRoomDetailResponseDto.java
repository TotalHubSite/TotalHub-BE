package com.totalhubsite.backend.domain.chat.dto.response;

import com.totalhubsite.backend.domain.chat.entity.ChatRoom;
import com.totalhubsite.backend.domain.chat.entity.type.ChatRoomType;
import lombok.Builder;

public record ChatRoomDetailResponseDto(
    Long chatRoomId,
    String name,
    ChatRoomType type
) {

    @Builder
    public ChatRoomDetailResponseDto(Long chatRoomId, String name, ChatRoomType type) {
        this.chatRoomId = chatRoomId;
        this.name = name;
        this.type = type;
    }

    public static ChatRoomDetailResponseDto fromEntity(ChatRoom entity) {
        return ChatRoomDetailResponseDto.builder()
            .chatRoomId(entity.getId())
            .name(entity.getName())
            .type(entity.getType())
            .build();
    }

}
