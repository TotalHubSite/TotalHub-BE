package com.totalhubsite.backend.domain.chat.controller;

import com.totalhubsite.backend.domain.chat.dto.request.ChatRoomRequestDto;
import com.totalhubsite.backend.domain.chat.dto.response.ChatRoomDetailResponseDto;
import com.totalhubsite.backend.domain.chat.dto.response.ChatRoomListResponseDto;
import com.totalhubsite.backend.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Void> chatRoomAdd(
        @RequestBody ChatRoomRequestDto requestDto
    ) {

        chatRoomService.addChatRoom(requestDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(null);
    }

    @GetMapping
    public ResponseEntity<Page<ChatRoomListResponseDto>> chatRoomList(
        Pageable pageable
    ) {
        Page<ChatRoomListResponseDto> responseDtos = chatRoomService.findChatRoomList(pageable);



        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDtos);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomDetailResponseDto> chatDetails(
        @PathVariable Long chatRoomId
    ) {
        ChatRoomDetailResponseDto responseDto = chatRoomService.findChatRoom(chatRoomId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

}


