package com.totalhubsite.backend.domain.chat.service;

import com.totalhubsite.backend.domain.chat.dto.ChatRoomRequestDto;
import com.totalhubsite.backend.domain.chat.entity.ChatRoom;
import com.totalhubsite.backend.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom addChatRoom(ChatRoomRequestDto request) {

        ChatRoom chatRoom = request.toEntity();

        return chatRoomRepository.save(chatRoom);
    }

}
