package com.iot.alertavital.healthtracking.infrastructure.repositories;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Disturbance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DisturbanceRepository extends JpaRepository<Disturbance, Long> {
    boolean existsDisturbanceByNameAndOnsetDate(String name, LocalDate onsetDate);
    List<Disturbance> findAllByPatient_User_IdOrderByOnsetDateDesc(Long id);
    Optional<Disturbance> findDisturbanceByIdAndPatient_User_Id(Long id, Long userId);

}
