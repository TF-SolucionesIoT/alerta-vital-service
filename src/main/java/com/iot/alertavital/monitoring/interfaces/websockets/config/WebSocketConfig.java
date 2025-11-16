package com.iot.alertavital.monitoring.interfaces.websockets.config;


import com.iot.alertavital.monitoring.interfaces.websockets.DataWebSocketHandler;
import com.iot.alertavital.monitoring.interfaces.websockets.FrontMonitoringWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketAuthInterceptor authInterceptor;
    private final DataWebSocketHandler dataHandler;
    private final FrontMonitoringWebSocketHandler frontMonitoringWebSocketHandler;

    public WebSocketConfig(WebSocketAuthInterceptor authInterceptor, DataWebSocketHandler dataHandler, FrontMonitoringWebSocketHandler frontMonitoringWebSocketHandler) {
        this.authInterceptor = authInterceptor;
        this.dataHandler = dataHandler;
        this.frontMonitoringWebSocketHandler = frontMonitoringWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("WebSocketConfig CARGADO");

        registry.addHandler(dataHandler, "/ws/edge")
                .setAllowedOrigins("*");

        registry.addHandler(frontMonitoringWebSocketHandler, "/ws/monitoring")
                .addInterceptors(authInterceptor)
                .setAllowedOrigins("*");
    }

}