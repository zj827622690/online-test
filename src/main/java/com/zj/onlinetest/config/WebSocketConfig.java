package com.zj.onlinetest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Auther: zj
 * @Date: 2019/4/18 10:47
 * @Description:
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /**
         * 订阅来自"/topic"和"/user"的消息，
         * /topic 单聊
         * /all 群聊
         */
        config.enableSimpleBroker("/topic","/all");

        /**
         * 客户端发送过来的消息，需要以"/app"为前缀，再经过Broker转发给响应的Controller
         */
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        /**
         * 路径"/websocket"被注册为STOMP端点，对外暴露，客户端通过该路径接入WebSocket服务
         */
        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
    }


}

