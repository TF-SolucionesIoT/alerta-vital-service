package com.iot.alertavital.profiles.application.internal;

import com.iot.alertavital.profiles.domain.model.aggregates.Profile;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import com.iot.alertavital.profiles.domain.services.ProfileQueryService;
import com.iot.alertavital.profiles.infrastructure.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return profileRepository.findById(id);
    }

    @Override
    public Optional<Profile> findByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public List<Profile> findByUserType(UserType userType) {
        return profileRepository.findByUserType(userType);
    }

    @Override
    public List<Profile> findAllCaregivers() {
        return profileRepository.findByUserType(UserType.CAREGIVER);
    }

    @Override
    public List<Profile> findAllPatients() {
        return profileRepository.findByUserType(UserType.PATIENT);
    }
}
