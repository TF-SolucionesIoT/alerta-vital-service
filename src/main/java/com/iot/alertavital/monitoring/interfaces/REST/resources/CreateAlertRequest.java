package com.iot.alertavital.monitoring.interfaces.REST.resources;

import java.time.Instant;

public record CreateAlertRequest(String deviceId, Instant dateTime) {
}
