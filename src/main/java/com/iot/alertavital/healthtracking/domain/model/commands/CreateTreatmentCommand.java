package com.iot.alertavital.healthtracking.domain.model.commands;

import java.time.LocalDateTime;

public record CreateTreatmentCommand(String name, String description, String frequency, String dosage, LocalDateTime startDate, LocalDateTime endDate, Boolean isActive) {
}

