package com.iot.alertavital.iam.infrastructure.repositories;

import com.iot.alertavital.iam.domain.model.aggregates.User;
import com.iot.alertavital.profiles.domain.model.valueobjects.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByEmail_Address(String email);
    boolean existsByEmail_Address(String email);
    List<User> findByUserType(UserType userType);
}