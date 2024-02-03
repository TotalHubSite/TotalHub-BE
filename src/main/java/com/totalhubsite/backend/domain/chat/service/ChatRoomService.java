package com.totalhubsite.backend.domain.chat.service;

import com.totalhubsite.backend.domain.chat.dto.request.ChatRoomRequestDto;
import com.totalhubsite.backend.domain.chat.dto.response.ChatRoomDetailResponseDto;
import com.totalhubsite.backend.domain.chat.dto.response.ChatRoomListResponseDto;
import com.totalhubsite.backend.domain.chat.entity.ChatRoom;
import com.totalhubsite.backend.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final RabbitAdmin rabbitAdmin; // RabbitMQ admin
    private final ChatRoomRepository chatRoomRepository;

    @Value("${spring.host_profile}")
    private String hostProfile;

    public ChatRoom addChatRoom(ChatRoomRequestDto request) {

        ChatRoom chatRoom = request.toEntity();

        // 채팅방에 대한 Exchange 생성
        TopicExchange exchange = new TopicExchange("chatroom." + chatRoom.getId());
        rabbitAdmin.declareExchange(exchange);

        return chatRoomRepository.save(chatRoom);
    }

    public Page<ChatRoomListResponseDto> findChatRoomList(Pageable pageable) {
        
        log.info("요청01 들어왔음!!!");
        log.info("요청02 들어왔음!!! : " + hostProfile);

        Page<ChatRoom> findEntity = chatRoomRepository.findAll(pageable);

        Page<ChatRoomListResponseDto> responseDtos = findEntity.map(ChatRoomListResponseDto::fromEntity);

        return responseDtos;

    }

    public ChatRoomDetailResponseDto findChatRoom(Long chatRoomId) {

        ChatRoom findEntity = chatRoomRepository.findById(chatRoomId)
                                        .orElseThrow(); // TODO: 예외처리필요

        ChatRoomDetailResponseDto responseDto = ChatRoomDetailResponseDto.fromEntity(findEntity);

        return responseDto;
    }
}
