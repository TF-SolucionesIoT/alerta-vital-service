package com.iot.alertavital.monitoring.infrastructure.repositories;

import com.iot.alertavital.monitoring.domain.model.entities.ReadingDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReadingDeviceRepository extends JpaRepository<ReadingDevice, Long> {
    List<ReadingDevice> findAllByDevice_Patient_IdOrderByCreatedAtDesc(Long patientId);
}
