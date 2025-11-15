package com.iot.alertavital.monitoring.interfaces.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.alertavital.monitoring.application.internal.outboundservices.RealTimeBroadcastService;
import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.monitoring.domain.model.commands.RecordVitalSignsCommand;
import com.iot.alertavital.monitoring.domain.model.queries.GetDeviceByIdQuery;
import com.iot.alertavital.monitoring.domain.services.DeviceQueryService;
import com.iot.alertavital.monitoring.interfaces.REST.resources.VitalSignRequest;
import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;


@Component
public class DataWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(DataWebSocketHandler.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final RealTimeBroadcastService realTimeBroadcastService;
    private final DeviceQueryService deviceQueryService;


    public DataWebSocketHandler(RealTimeBroadcastService realTimeBroadcastService, DeviceQueryService deviceQueryService) {
        this.realTimeBroadcastService = realTimeBroadcastService;
        this.deviceQueryService = deviceQueryService;
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

            var deviceOpt = deviceQueryService.findByDeviceId(new GetDeviceByIdQuery(dto.deviceId()));
            if (deviceOpt.isEmpty()) {
                logger.warn("DeviceId no encontrado, mensaje ignorado: {}", dto.deviceId());

                //session.sendMessage(new TextMessage("Advertencia: deviceId no registrado, mensaje ignorado"));
                return;
            }

            var device = deviceOpt.get();
            var user = device.getPatient().getUser().getId();

            Map<String, Object> message_to_frontend = new HashMap<>();

            message_to_frontend.put("user_id", user);
            message_to_frontend.put("bpm", dto.bpm());
            message_to_frontend.put("spo2", dto.spo2());


            //var command = new RecordVitalSignsCommand(dto.bpm(), dto.spo2());
            //recordService.handle(command);


            //send to front end
            realTimeBroadcastService.broadcast(mapper.writeValueAsString(message_to_frontend));


        } catch (Exception e) {
            logger.error("Error procesando mensaje", e);

        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        logger.info("Cliente desconectado: " + session.getId() + " con estado " + status);

    }
}