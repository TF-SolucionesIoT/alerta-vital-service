package com.iot.alertavital.iam.application.internal;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.iam.domain.services.UserQueryService;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import com.iot.alertavital.iam.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail_Address(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByUserType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

    @Override
    public List<User> findAllCaregivers() {
        return userRepository.findByUserType(UserType.CAREGIVER);
    }

    @Override
    public List<User> findAllPatients() {
        return userRepository.findByUserType(UserType.PATIENT);
    }
}
