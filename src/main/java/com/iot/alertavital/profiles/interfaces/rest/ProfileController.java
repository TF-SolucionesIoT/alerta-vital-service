package com.iot.alertavital.profiles.interfaces.rest;

import com.iot.alertavital.profiles.domain.model.commands.UpdateCaregiverCommand;
import com.iot.alertavital.profiles.domain.model.commands.UpdatePatientCommand;
import com.iot.alertavital.profiles.interfaces.ACL.ProfilesContextFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/profiles")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final ProfilesContextFacade profilesContextFacade;

    public ProfileController(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    @GetMapping("/caregivers/{caregiverId}")
    public ResponseEntity<?> getCaregiverById(@PathVariable Long caregiverId) {
        try {
            var caregiver = profilesContextFacade.findCaregiverById(caregiverId);
            if (caregiver.isPresent()) {
                return ResponseEntity.ok(new CaregiverResponse(
                    caregiver.get().getId(),
                    caregiver.get().getUser().fullName(),
                    caregiver.get().getUser().email(),
                    caregiver.get().getUser().getUsername(),
                    caregiver.get().getPhoneNumber().number()
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping("/caregivers/by-user/{userId}")
    public ResponseEntity<?> getCaregiverByUserId(@PathVariable Long userId) {
        try {
            var caregiver = profilesContextFacade.findCaregiverByUserId(userId);
            if (caregiver.isPresent()) {
                return ResponseEntity.ok(new CaregiverResponse(
                    caregiver.get().getId(),
                    caregiver.get().getUser().fullName(),
                    caregiver.get().getUser().email(),
                    caregiver.get().getUser().getUsername(),
                    caregiver.get().getPhoneNumber().number()
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable Long patientId) {
        try {
            var patient = profilesContextFacade.findPatientById(patientId);
            if (patient.isPresent()) {
                return ResponseEntity.ok(new PatientResponse(
                    patient.get().getId(),
                    patient.get().getUser().fullName(),
                    patient.get().getUser().email(),
                    patient.get().getUser().getUsername(),
                    patient.get().getBirthday().birthday()
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping("/patients/by-user/{userId}")
    public ResponseEntity<?> getPatientByUserId(@PathVariable Long userId) {
        try {
            var patient = profilesContextFacade.findPatientByUserId(userId);
            if (patient.isPresent()) {
                return ResponseEntity.ok(new PatientResponse(
                    patient.get().getId(),
                    patient.get().getUser().fullName(),
                    patient.get().getUser().email(),
                    patient.get().getUser().getUsername(),
                    patient.get().getBirthday().birthday()
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping("/caregivers/{caregiverId}")
    public ResponseEntity<String> updateCaregiver(
            @PathVariable Long caregiverId,
            @RequestBody UpdateCaregiverRequest request) {

        try {
            var command = new UpdateCaregiverCommand(caregiverId, request.phoneNumber());
            profilesContextFacade.updateCaregiver(command);
            return ResponseEntity.ok("Información del cuidador actualizada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping("/patients/{patientId}")
    public ResponseEntity<String> updatePatient(
            @PathVariable Long patientId,
            @RequestBody UpdatePatientRequest request) {

        try {
            var command = new UpdatePatientCommand(patientId, request.birthday());
            profilesContextFacade.updatePatient(command);
            return ResponseEntity.ok("Información del paciente actualizada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    // DTOs internos
    public record UpdateCaregiverRequest(String phoneNumber) {}

    public record UpdatePatientRequest(LocalDate birthday) {}

    public record CaregiverResponse(Long id, String fullName, String email, String username, String phoneNumber) {}

    public record PatientResponse(Long id, String fullName, String email, String username, LocalDate birthday) {}
}
