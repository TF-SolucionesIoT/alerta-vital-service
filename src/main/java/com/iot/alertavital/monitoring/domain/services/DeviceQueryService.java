package com.iot.alertavital.monitoring.domain.services;

import com.iot.alertavital.monitoring.domain.model.aggregates.Device;
import com.iot.alertavital.monitoring.domain.model.queries.GetDeviceByIdQuery;

import java.util.Optional;

public interface DeviceQueryService {
    Optional<Device> findByDeviceId(GetDeviceByIdQuery query);
}
