package com.goldenelectric.manager.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.goldenelectric.manager.handlers.OcppHandler;
import com.goldenelectric.manager.handlers.UserHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    private final WebSocketHandler ocppWsHandler;
    private final WebSocketHandler userWsHandler;

    public WebSocketConfig(@Qualifier("ocppWsHandler") WebSocketHandler ocppWsHandler, @Qualifier("userWsHandler") WebSocketHandler userWsHandler) {
        this.ocppWsHandler = ocppWsHandler;
        this.userWsHandler = userWsHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ocppWsHandler, "/ocpp/{cpUser}/{charger_id}").setAllowedOrigins("*");
        registry.addHandler(userWsHandler, "/user/{user_id}").setAllowedOrigins("*");
    }   

    @Bean
    public WebSocketHandler ocppWsHandler(){
        return new OcppHandler();
    };

    @Bean
    public WebSocketHandler userWsHandler(){
        return new UserHandler();
    }
}
