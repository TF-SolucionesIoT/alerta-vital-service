package com.iot.alertavital.monitoring.domain.model.commands;

import java.time.LocalDateTime;

public record CreateReadingDeviceCommand(Integer spO2, Integer bpm, String deviceId, Integer bpDiastolic, Integer bpSystolic) {

}
