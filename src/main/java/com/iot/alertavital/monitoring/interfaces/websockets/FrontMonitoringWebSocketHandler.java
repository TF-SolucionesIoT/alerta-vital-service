package com.iot.alertavital.monitoring.interfaces.websockets;

import com.iot.alertavital.monitoring.application.internal.outboundservices.RealTimeBroadcastService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class FrontMonitoringWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(FrontMonitoringWebSocketHandler.class);

    private final RealTimeBroadcastService broadcastService;

    public FrontMonitoringWebSocketHandler(RealTimeBroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Frontend conectado: {}", session.getId());

        Long userId = (Long) session.getAttributes().get("userId");
        String userType = (String) session.getAttributes().get("userType");
        Long patientId = (Long) session.getAttributes().get("patientId");

        broadcastService.registerSession(session);
        broadcastService.setUserForSession(session, userId, patientId, userType);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Frontend desconectado {}", session.getId());
        broadcastService.removeSession(session);
    }

    // El frontend no envía mensajes, solo escucha
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // No hacemos nada
    }
}