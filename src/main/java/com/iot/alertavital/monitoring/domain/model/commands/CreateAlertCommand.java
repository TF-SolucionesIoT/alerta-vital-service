package com.iot.alertavital.monitoring.domain.model.commands;

import java.time.Instant;

public record CreateAlertCommand(String deviceId, Instant dateTime) {
}
