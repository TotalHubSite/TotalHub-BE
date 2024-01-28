package com.totalhubsite.backend.domain.chat.controller;

import com.totalhubsite.backend.domain.chat.dto.ChatRoomRequestDto;
import com.totalhubsite.backend.domain.chat.entity.ChatRoom;
import com.totalhubsite.backend.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ChatRoom chatRoomAdd(
        @RequestBody ChatRoomRequestDto requestDto
    ) {

        ChatRoom response = chatRoomService.addChatRoom(requestDto);

        return response;
    }

}
