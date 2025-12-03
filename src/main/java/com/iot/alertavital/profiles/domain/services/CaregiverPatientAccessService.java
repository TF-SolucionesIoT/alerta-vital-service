package com.iot.alertavital.profiles.domain.services;

import java.util.List;

public interface CaregiverPatientAccessService {
    boolean canAccessPatient(Long caregiverId, Long patientId);
    List<Long> getAccessiblePatientWithUserId();
}
