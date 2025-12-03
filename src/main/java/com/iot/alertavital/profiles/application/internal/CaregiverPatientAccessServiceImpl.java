package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.domain.services.CaregiverPatientAccessService;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverPatientAccessRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaregiverPatientAccessServiceImpl implements CaregiverPatientAccessService {

    private final CaregiverPatientAccessRepository caregiverPatientAccessRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final CaregiverRepository caregiverRepository;
    private final PatientRepository patientRepository;


    public CaregiverPatientAccessServiceImpl(CaregiverPatientAccessRepository caregiverPatientAccessRepository, AuthenticatedUserProvider authenticatedUserProvider, CaregiverRepository caregiverRepository, PatientRepository patientRepository) {
        this.caregiverPatientAccessRepository = caregiverPatientAccessRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.caregiverRepository = caregiverRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public boolean canAccessPatient(Long caregiverId, Long patientId) {
        return caregiverPatientAccessRepository.existsByCaregiverIdAndPatientId(caregiverId, patientId);
    }

    @Override
    public List<Long> getAccessiblePatientWithUserId() {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        // 1. Obtener caregiver desde userId
        var caregiver = caregiverRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));

        var accessList = caregiverPatientAccessRepository.findByCaregiverId(caregiver.getId());

        return accessList.stream()
                .map(access -> patientRepository.findById(access.getPatientId())
                        .map(p -> p.getUser().getId())
                        .orElseThrow(() -> new IllegalStateException("Patient not found"))
                )
                .toList();
    }
}
