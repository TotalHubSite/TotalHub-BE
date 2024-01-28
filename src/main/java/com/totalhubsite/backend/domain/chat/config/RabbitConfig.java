package com.totalhubsite.backend.domain.chat.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit // RabbitMQ를 사용하기 위해 필요한 설정을 활성화
public class RabbitConfig implements RabbitListenerConfigurer {

    // AmqpAdmin 빈은 RabbitMQ의 관리 작업을 수행하며, RabbitTemplate 빈은 메시지를 전송하는데 사용된다.

    // RabbitAdmin은 Spring AMQP의 AmqpAdmin을 상속하고, RabbitMQ 관리 작업을 수행
    // 예를 들어, 큐 생성, 삭제, 교환 설정, 바인딩 등의 작업을 수행
    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    // RabbitTemplate은 RabbitMQ에 메시지를 전송하는데 사용되는 주요 클래스
    // 메시지 변환기를 설정하여 메시지를 JSON 형식으로 전송할 수 있게 한다.
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    // ConnectionFactory는 RabbitMQ 서버와의 모든 연결을 관리
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("docker-compose_rabbitmq_1");
        // factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    // Java 객체를 JSON 형식의 메시지로 변환
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.registerModule(dateTimeModule());
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        return converter;
    }

    // JSON 형식의 메시지를 Java 객체로 변환
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public Module dateTimeModule() {
        return new JavaTimeModule();
    }





    // @RabbitListener 어노테이션은 메시지 브로커로부터 메시지를 비동기적으로 수신할 메소드에 붙는 어노테이션
    // RabbitMQ로 부텅 직접 메시지를 받아 처리할 때(이벤트 발생) 같은 경우에 RabbitMQ로 부터 받는 메시지 처리 설정

    // 아래 설정들은 아직 당장 안씀

    // @RabbitListener 어노테이션이 붙은 메소드를 관리하는 레지스트리(객체 운영관리 - 생명주기, 접근 등)
    @Bean
    public RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry() {
        return new RabbitListenerEndpointRegistry();
    }

    // @RabbitListener 어노테이션이 붙은 메소드를 처리하는 팩토리(객체 생성관리)
    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    // RabbitListenerEndpointRegistrar를 설정 이 설정을 통해 @RabbitListener를 사용할 때 메시지 처리 방식을 정의할 수 있다.
    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(1); // 한 번에 처리할 메시지 수
        factory.setConsecutiveActiveTrigger(1); // 메시지 처리를 위한 리스너 컨테이너의 스케줄링 관련 설정
        factory.setConsecutiveIdleTrigger(1); // 메시지 처리를 위한 리스너 컨테이너의 스케줄링 관련 설정
        factory.setConnectionFactory(connectionFactory()); // ConnectionFactory는 RabbitMQ 서버와의 연결을 관리하는 인스턴스
        registrar.setContainerFactory(factory); // 메시지를 처리하는 컨테이너를 생성하는 방식을 설정
        registrar.setEndpointRegistry(rabbitListenerEndpointRegistry());
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
