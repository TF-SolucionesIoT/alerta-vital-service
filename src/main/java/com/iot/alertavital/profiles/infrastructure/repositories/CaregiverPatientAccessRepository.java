package com.iot.alertavital.profiles.infrastructure.repositories;

import com.iot.alertavital.profiles.domain.model.aggregates.CaregiverPatientAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverPatientAccessRepository extends JpaRepository<CaregiverPatientAccess, Long> {
    boolean existsByCaregiverIdAndPatientId(Long caregiverId, Long patientId);
}
