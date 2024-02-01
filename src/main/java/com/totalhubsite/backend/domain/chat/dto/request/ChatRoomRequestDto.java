package com.totalhubsite.backend.domain.chat.dto.request;

import com.totalhubsite.backend.domain.chat.entity.ChatRoom;
import com.totalhubsite.backend.domain.chat.entity.type.ChatRoomType;
import lombok.Builder;

public record ChatRoomRequestDto(
    String name,
    ChatRoomType type
) {

    @Builder
    public ChatRoomRequestDto(String name, ChatRoomType type) {
        this.name = name;
        this.type = type;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
            .name(name)
            .type(type)
            .build();
    }

}
