package com.iot.alertavital.profiles.interfaces.REST;

import com.iot.alertavital.profiles.domain.model.entities.PatientInviteCode;
import com.iot.alertavital.profiles.domain.services.InviteCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Obtener pacientes enlazados (caregiver)
    @GetMapping("/linked-patients")
    public ResponseEntity<?> getLinkedPatients() {
        List<Map<String, Object>> patients = inviteCodeService.getLinkedPatients();
        return ResponseEntity.ok(patients);
    }
}
