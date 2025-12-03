package com.iot.alertavital.profiles.interfaces.REST;

import com.iot.alertavital.profiles.domain.services.CaregiverPatientAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/access")
public class CaregiverAccessController {

    private final CaregiverPatientAccessService caregiverAccessService;

    public CaregiverAccessController(CaregiverPatientAccessService caregiverAccessService) {
        this.caregiverAccessService = caregiverAccessService;
    }


    @GetMapping("/patients")
    public ResponseEntity<List<Long>> getAccessiblePatients() {
        List<Long> patientUserIds = caregiverAccessService.getAccessiblePatientWithUserId();
        return ResponseEntity.ok(patientUserIds);
    }
}
