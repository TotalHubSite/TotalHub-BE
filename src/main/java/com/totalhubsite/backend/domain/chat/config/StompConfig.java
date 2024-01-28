package com.totalhubsite.backend.domain.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// WebSocket & Stomp 설정

@Configuration
@EnableWebSocketMessageBroker // 웹소켓 메시지 브로커를 활성화
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    // WebSocket 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // ws 주소로 웹소켓 연결
            .setAllowedOrigins("http://localhost:3000") // CORS 설정
            .setAllowedOrigins("http://15.165.144.39") // CORS 설정
            .withSockJS(); // SockJS를 사용
    }

    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트에서 "/app"으로 시작하는 주소로 메시지를 보내면, 그 메시지를 메시지 핸들러가 처리하게 됨
        // 이걸로 메시지를 보내는것 ex. /app/news => news에 메시지
        registry.setApplicationDestinationPrefixes("/app");
        // STOMP 브로커를 활성화하고, 클라이언트가 "/topic"으로 시작하는 주소로 구독하면, 해당 주소로 메시지를 전달받음
        // 이걸로 구독을하는것  ex. /topic/news => news에 구독
        registry.enableStompBrokerRelay("/topic")
            .setRelayHost("docker-compose_rabbitmq_1")
//            .setRelayHost("localhost")
            .setRelayPort(61613)  // STOMP 플러그인의 기본포트 : 왜냐면 스프링에서 STOMP를 쓰고 메시지브로커로서 RabbitMQ를 쓰니깐
            .setClientLogin("guest")
            .setClientPasscode("guest");
    }

}
