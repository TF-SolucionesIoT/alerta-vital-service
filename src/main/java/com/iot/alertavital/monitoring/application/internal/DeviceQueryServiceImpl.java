package com.iot.alertavital.monitoring.application.internal;

import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.monitoring.domain.model.queries.GetDeviceByIdQuery;
import com.iot.alertavital.monitoring.domain.services.DeviceQueryService;
import com.iot.alertavital.monitoring.infrastructure.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {
    private final DeviceRepository deviceRepository;
    public DeviceQueryServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    @Override
    public Optional<Device> findByDeviceId(GetDeviceByIdQuery query) {
        var device = deviceRepository.findByDeviceId(query.deviceId());
        return Optional.ofNullable(device);
    }
}
