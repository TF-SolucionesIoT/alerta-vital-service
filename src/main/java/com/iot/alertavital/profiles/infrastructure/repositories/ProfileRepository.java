package com.iot.alertavital.profiles.infrastructure.repositories;

import com.iot.alertavital.profiles.domain.model.aggregates.Profile;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long userId);
    List<Profile> findByUserType(UserType userType);
    boolean existsByUserId(Long userId);
}
