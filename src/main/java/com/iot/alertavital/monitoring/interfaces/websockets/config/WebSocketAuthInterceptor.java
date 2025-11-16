package com.iot.alertavital.monitoring.interfaces.websockets.config;

import com.iot.alertavital.iam.domain.services.JwtService;
import com.iot.alertavital.iam.infrastructure.jwt.JwtServiceImpl;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;
    private final PatientRepository patientRepository;

    public WebSocketAuthInterceptor(JwtService jwtService, PatientRepository patientRepository) {
        this.jwtService = jwtService;
        this.patientRepository = patientRepository;
    }


    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {


        System.out.println("HANDSHAKE RECIBIDO !!!");
        var uri = request.getURI();
        var query = uri.getQuery(); // token=abcdef...

        if (query == null || !query.startsWith("token=")) {
            return false;
        }

        String token = query.substring("token=".length());

        // validar JWT
        if (!jwtService.isTokenValid(token)) {
            return false;
        }

        // extraer userId
        Long userId = Long.valueOf(jwtService.extractUserId(token));

        // extraer tipo de usuario del claim
        String userType = Jwts.parserBuilder()   // usando la misma key
                .setSigningKey(((JwtServiceImpl)jwtService).getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("typeOfUser", String.class);

        // guardas en atributos de la sesión
        attributes.put("userId", userId);
        attributes.put("userType", userType);
        attributes.put("token", token);

        // 🔥 NUEVO: obtener patientId si el usuario es paciente
        Long patientId = null;

        if ("PATIENT".equals(userType)) {
            patientId = patientRepository.findByUser_Id(userId)
                    .map(patient -> patient.getId())
                    .orElse(null);
        }

        attributes.put("patientId", patientId);


        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {}
}
