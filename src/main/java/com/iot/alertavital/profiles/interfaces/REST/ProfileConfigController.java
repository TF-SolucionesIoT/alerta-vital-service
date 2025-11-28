package com.iot.alertavital.profiles.interfaces.REST;


import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.domain.services.ProfileConfigService;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverPatientAccessRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import com.iot.alertavital.profiles.interfaces.REST.resources.ChangePasswordRequest;
import com.iot.alertavital.profiles.interfaces.REST.transform.ProfileConfigCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/me")
public class ProfileConfigController {
    private final ProfileConfigService profileConfigService;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final CaregiverRepository caregiverRepository;
    private final CaregiverPatientAccessRepository accessRepository;
    private final PatientRepository patientRepository;

    public ProfileConfigController(
            ProfileConfigService profileConfigService,
            AuthenticatedUserProvider authenticatedUserProvider,
            CaregiverRepository caregiverRepository,
            CaregiverPatientAccessRepository accessRepository,
            PatientRepository patientRepository) {
        this.profileConfigService = profileConfigService;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.caregiverRepository = caregiverRepository;
        this.accessRepository = accessRepository;
        this.patientRepository = patientRepository;
    }



    @PostMapping("/change-password")
    public ResponseEntity<Map<String,String>> changePassword(@RequestBody ChangePasswordRequest request){
        profileConfigService.handle(ProfileConfigCommandFromResourceAssembler.toCommand(request));
        return ResponseEntity.ok(Map.of("message","Password has been updated"));
    }

    @GetMapping("/caregiver/patients")
    public ResponseEntity<?> getCaregiverPatients() {
        Long currentUserId = authenticatedUserProvider.getCurrentUserId();
        String userType = authenticatedUserProvider.getCurrentUserType();

        if (!userType.equals("CAREGIVER")) {
            return ResponseEntity.status(403).build();
        }

        var caregiver = caregiverRepository.findByUser_Id(currentUserId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));

        var accesses = accessRepository.findByCaregiverId(caregiver.getId());

        List<Map<String, Object>> patients = accesses.stream()
                .map(access -> {
                    var patient = patientRepository.findById(access.getPatientId())
                            .orElseThrow(() -> new RuntimeException("Patient not found"));
                    return Map.<String, Object>of(
                            "patientId", patient.getId(),
                            "patientName", patient.getUser().fullName(),
                            "accessLevel", "read",
                            "grantedDate", access.getCreatedAt().toString()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(patients);
    }

}


