package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.aggregates.Profile;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> findById(Long id);
    Optional<Profile> findByUserId(Long userId);
    List<Profile> findAll();
    List<Profile> findByUserType(UserType userType);
    List<Profile> findAllCaregivers();
    List<Profile> findAllPatients();
}
