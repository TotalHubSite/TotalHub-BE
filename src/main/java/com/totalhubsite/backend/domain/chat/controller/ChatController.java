package com.totalhubsite.backend.domain.chat.controller;

import com.totalhubsite.backend.domain.chat.dto.ChatMessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/room")
    public ChatMessageRequestDto sendMessage(@Payload ChatMessageRequestDto chatMessage) {
        return chatMessage;
    }

}
