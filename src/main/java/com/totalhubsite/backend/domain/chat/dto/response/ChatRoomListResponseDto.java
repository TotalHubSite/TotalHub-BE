package com.totalhubsite.backend.domain.chat.dto.response;

import com.totalhubsite.backend.domain.chat.entity.ChatRoom;
import com.totalhubsite.backend.domain.chat.entity.type.ChatRoomType;
import lombok.Builder;

public record ChatRoomListResponseDto(
    Long chatRoomId,
    String name,
    ChatRoomType type
) {

    @Builder
    public ChatRoomListResponseDto(Long chatRoomId, String name, ChatRoomType type) {
        this.chatRoomId = chatRoomId;
        this.name = name;
        this.type = type;
    }

    public static ChatRoomListResponseDto fromEntity(ChatRoom entity) {
        return ChatRoomListResponseDto.builder()
            .chatRoomId(entity.getId())
            .name(entity.getName())
            .type(entity.getType())
            .build();
    }
}
