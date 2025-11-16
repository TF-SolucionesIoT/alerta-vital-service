package com.iot.alertavital.monitoring.application.internal.outboundservices;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.infrastructure.security.CustomUserPrincipal;
import com.iot.alertavital.profiles.domain.model.aggregates.CaregiverPatientAccess;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverPatientAccessRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RealTimeBroadcastService {
    private final Map<WebSocketSession, SessionUser> sessionUsers = new ConcurrentHashMap<>();

    private final CaregiverRepository caregiverRepository;
    private final CaregiverPatientAccessRepository patientAccessRepository;

    public RealTimeBroadcastService(CaregiverRepository caregiverRepository, CaregiverPatientAccessRepository patientAccessRepository) {
        this.caregiverRepository = caregiverRepository;
        this.patientAccessRepository = patientAccessRepository;
    }

    public void registerSession(WebSocketSession session) {
        sessionUsers.putIfAbsent(session, new SessionUser(null, null, null));
        //
    }

    public void removeSession(WebSocketSession session) {
        sessionUsers.remove(session);
    }

    public void setUserForSession(WebSocketSession session, Long userId, Long patientId, String type) {
        sessionUsers.put(session, new SessionUser(userId, patientId, type));
    }

    public SessionUser getUserForSession(WebSocketSession session) {
        return sessionUsers.get(session);
    }

    public void broadcast(Long patientId, String json) {
        sessionUsers.forEach((session, user) -> {
            try {
                if (user == null) return;
                // PACIENTE conectado
                if ("PATIENT".equals(user.getType())) {
                    // match patientId
                    if (user.getPatientId() != null && user.getPatientId().equals(patientId)) {
                        session.sendMessage(new TextMessage(json));
                    }
                }
                // CAREGIVER conectado
                else if ("CAREGIVER".equals(user.getType())) {

                    var caregiver = caregiverRepository.findByUser_Id(user.getUserId());

                    if (caregiver.isEmpty()) {
                        return;
                    }

                    List<Long> allowedPatients =
                            patientAccessRepository.findByCaregiverId(caregiver.get().getId())
                                    .stream()
                                    .map(CaregiverPatientAccess::getPatientId)
                                    .toList();

                    System.out.println("Caregiver " + user.getUserId() + " allowedPatients: " + allowedPatients);


                    if (allowedPatients.contains(patientId)) {
                        session.sendMessage(new TextMessage(json));
                    }
                }
            } catch (Exception e) {
                // Log para depuración
                System.err.println("Error enviando mensaje WebSocket: " + e.getMessage());
            }
        });
    }



    @Getter
    public static class SessionUser {
        private final Long userId;
        private final Long patientId;
        private final String type;

        public SessionUser(Long id, Long patientId, String type) {
            this.userId = id;
            this.patientId = patientId;
            this.type = type;
        }

    }

}


