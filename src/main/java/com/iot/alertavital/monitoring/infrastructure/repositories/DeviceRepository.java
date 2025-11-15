package com.iot.alertavital.monitoring.infrastructure.repositories;

import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    boolean existsByDeviceId(String deviceId);
    Device findByDeviceId(String deviceId);
}
