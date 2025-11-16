package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.profiles.domain.services.CaregiverPatientAccessService;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverPatientAccessRepository;
import org.springframework.stereotype.Service;

@Service
public class CaregiverPatientAccessServiceImpl implements CaregiverPatientAccessService {

    private final CaregiverPatientAccessRepository caregiverPatientAccessRepository;

    public CaregiverPatientAccessServiceImpl(CaregiverPatientAccessRepository caregiverPatientAccessRepository) {
        this.caregiverPatientAccessRepository = caregiverPatientAccessRepository;
    }

    @Override
    public boolean canAccessPatient(Long caregiverId, Long patientId) {
        return caregiverPatientAccessRepository.existsByCaregiverIdAndPatientId(caregiverId, patientId);
    }
}
