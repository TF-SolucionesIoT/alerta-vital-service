package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;
import com.iot.alertavital.profiles.domain.services.CaregiverQueryService;
import com.iot.alertavital.profiles.infrastructure.repositories.CaregiverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaregiverQueryServiceImpl implements CaregiverQueryService {

    private final CaregiverRepository caregiverRepository;

    public CaregiverQueryServiceImpl(CaregiverRepository caregiverRepository) {
        this.caregiverRepository = caregiverRepository;
    }

    @Override
    public Optional<Caregiver> findById(Long id) {
        return caregiverRepository.findById(id);
    }

    @Override
    public Optional<Caregiver> findByUserId(Long userId) {
        return caregiverRepository.findByUserId(userId);
    }
}
