//package com.example.datingtrap.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // Endpoint này chính là /ws
//        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        // Tin nhắn gửi tới client sẽ bắt đầu bằng /topic
//        config.enableSimpleBroker("/topic");
//        // Tin nhắn từ client gửi lên server sẽ bắt đầu bằng /app
//        config.setApplicationDestinationPrefixes("/app");
//    }
//}
