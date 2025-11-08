package com.iot.alertavital.healthtracking.infrastructure.repositories;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Treatments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentsRepository extends JpaRepository<Treatments, Long> {
    List<Treatments> findAllByPatient_User_IdOrderByStartDateDesc(Long id);
}

