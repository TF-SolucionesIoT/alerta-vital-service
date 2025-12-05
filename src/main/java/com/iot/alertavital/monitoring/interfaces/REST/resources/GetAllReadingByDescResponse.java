package com.iot.alertavital.monitoring.interfaces.REST.resources;

import java.time.Instant;
import java.time.LocalDateTime;

public record GetAllReadingByDescResponse(Long id, Integer spO2, Integer pulse, Integer bpDiastolic, Integer bpSystolic, LocalDateTime createdAt) {

}
