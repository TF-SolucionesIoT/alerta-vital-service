package com.iot.alertavital.monitoring.domain.services;

import com.iot.alertavital.monitoring.domain.model.aggregates.VitalSign;
import com.iot.alertavital.monitoring.domain.model.commands.RecordVitalSignsCommand;

public interface VitalSignCommandService {
    public VitalSign handle(RecordVitalSignsCommand command);
}
