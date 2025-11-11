package com.iot.alertavital.monitoring.domain.services;

import com.iot.alertavital.monitoring.domain.model.entities.ReadingDevice;
import com.iot.alertavital.monitoring.domain.model.queries.GetAllReadingByDescQuery;

import java.util.List;
import java.util.Optional;

public interface ReadingDeviceQueryService {
    List<ReadingDevice> handle(GetAllReadingByDescQuery query);
}
