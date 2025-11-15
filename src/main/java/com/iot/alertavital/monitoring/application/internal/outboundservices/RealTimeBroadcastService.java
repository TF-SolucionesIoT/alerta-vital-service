package com.iot.alertavital.monitoring.application.internal.outboundservices;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RealTimeBroadcastService {
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    public void registerSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void broadcast(String json) {
        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(json));
            } catch (Exception ignored) {}
        });
    }

}
