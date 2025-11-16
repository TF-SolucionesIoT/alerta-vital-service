package com.iot.alertavital.profiles.domain.services;

public interface CaregiverPatientAccessService {
    boolean canAccessPatient(Long caregiverId, Long patientId);
}
