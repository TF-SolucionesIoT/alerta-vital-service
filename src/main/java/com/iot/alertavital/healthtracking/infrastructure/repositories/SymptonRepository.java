package com.iot.alertavital.healthtracking.infrastructure.repositories;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Sympton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SymptonRepository extends JpaRepository<Sympton, Long> {
    boolean existsSymptonByNameAndOnsetDate(String name, LocalDateTime onsetDate);
    List<Sympton> findAllByPatient_User_IdOrderByOnsetDateDesc(Long id);
    Optional<Sympton> findSymptonByIdAndPatient_User_Id(Long id, Long userId);

}

