package com.iot.alertavital.monitoring.interfaces.REST.resources;

import java.time.Instant;

public record GetAllReadingByDescResponse(Long id, Integer sp02, Integer pulse, Instant timestamp) {
}
