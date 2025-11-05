package com.iot.alertavital.profiles.infrastructure.repositories;


import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
