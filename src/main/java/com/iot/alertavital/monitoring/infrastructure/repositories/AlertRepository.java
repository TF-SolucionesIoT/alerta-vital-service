package com.iot.alertavital.monitoring.infrastructure.repositories;

import com.iot.alertavital.monitoring.domain.model.entities.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByDeviceIdOrderByTimestampDesc(String deviceId);

}
