package com.iot.alertavital.profiles.infrastructure.repositories;

import com.iot.alertavital.profiles.domain.model.aggregates.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
}
