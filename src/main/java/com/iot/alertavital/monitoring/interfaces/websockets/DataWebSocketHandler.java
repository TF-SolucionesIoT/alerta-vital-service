package com.iot.alertavital.monitoring.interfaces.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.alertavital.monitoring.application.internal.outboundservices.RealTimeBroadcastService;
import com.iot.alertavital.monitoring.domain.model.commands.RecordVitalSignsCommand;
import com.iot.alertavital.monitoring.interfaces.REST.resources.VitalSignRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class DataWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(DataWebSocketHandler.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final RealTimeBroadcastService realTimeBroadcastService;



    public DataWebSocketHandler(RealTimeBroadcastService realTimeBroadcastService) {
        this.realTimeBroadcastService = realTimeBroadcastService;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Cliente conectado: {}", session.getId());

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            logger.info("Mensaje recibido del cliente {}: {}", session.getId(), payload);

            var dto = mapper.readValue(payload, VitalSignRequest.class);

            //var command = new RecordVitalSignsCommand(dto.bpm(), dto.spo2());
            //recordService.handle(command);


            //send to front end
            realTimeBroadcastService.broadcast(payload);


        } catch (Exception e) {
            logger.error("Error procesando mensaje", e);

        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        logger.info("Cliente desconectado: " + session.getId() + " con estado " + status);

    }
}