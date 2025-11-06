package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;

import java.util.Optional;

public interface CaregiverQueryService {
    Optional<Caregiver> findById(Long id);
    Optional<Caregiver> findByUserId(Long userId);
}
