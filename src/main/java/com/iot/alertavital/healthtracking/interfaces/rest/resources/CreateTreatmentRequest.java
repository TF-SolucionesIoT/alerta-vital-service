package com.iot.alertavital.healthtracking.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateTreatmentRequest(String name, String description, String frequency, String dosage, LocalDateTime startDate, LocalDateTime endDate, Boolean isActive) {
}

