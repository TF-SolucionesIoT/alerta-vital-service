package com.iot.alertavital.iam.domain.services;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findByUserType(UserType userType);
    List<User> findAllCaregivers();
    List<User> findAllPatients();
}
