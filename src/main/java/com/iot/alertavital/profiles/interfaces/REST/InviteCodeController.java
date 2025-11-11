package com.iot.alertavital.profiles.interfaces.REST;

import com.iot.alertavital.profiles.domain.model.entities.PatientInviteCode;
import com.iot.alertavital.profiles.domain.services.InviteCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/invite")
public class InviteCodeController {

    private final InviteCodeService inviteCodeService;

    public InviteCodeController(InviteCodeService inviteCodeService) {
        this.inviteCodeService = inviteCodeService;
    }

    // Generar código (paciente)
    @PostMapping("/generate")
    public ResponseEntity<?> generateCode() {
        PatientInviteCode code = inviteCodeService.generateCode();
        return ResponseEntity.ok(Map.of(
                "code", code.getCode(),
                "expiresAt", code.getExpiresAt()
        ));
    }

    // Usar código (caregiver)
    @PostMapping("/use/{code}")
    public ResponseEntity<?> useCode(@PathVariable String code) {
        String message = inviteCodeService.useCode(code);
        return ResponseEntity.ok(Map.of("message", message));
    }
}
