package com.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {

		config.enableSimpleBroker("/topic");

		// /topic/messages

		config.setApplicationDestinationPrefixes("/app");

		// /app/chat
		// server-side: @MessageMapping("/chat");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat")   // connection establishment
		.setAllowedOrigins("http://localhost:3000")
		.withSockJS();

	}
	// /chat endpoint per connection stablished
}
