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

    private final DataWebSocketHandler dataHandler;
    private final FrontMonitoringWebSocketHandler frontMonitoringWebSocketHandlerhandler;

    public WebSocketConfig(DataWebSocketHandler dataHandler, FrontMonitoringWebSocketHandler frontMonitoringWebSocketHandlerhandler) {
        this.dataHandler = dataHandler;
        this.frontMonitoringWebSocketHandlerhandler = frontMonitoringWebSocketHandlerhandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(dataHandler, "/ws/edge").setAllowedOrigins("*");
        registry.addHandler(frontMonitoringWebSocketHandlerhandler, "/ws/monitoring").setAllowedOrigins("*");
    }

}