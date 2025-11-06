package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;

import java.util.Optional;

public interface PatientQueryService {
    Optional<Patient> findById(Long id);
    Optional<Patient> findByUserId(Long userId);
}
