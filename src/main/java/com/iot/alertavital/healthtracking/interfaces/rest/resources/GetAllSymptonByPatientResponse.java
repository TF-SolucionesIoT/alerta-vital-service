package com.iot.alertavital.healthtracking.interfaces.rest.resources;

import java.time.LocalDateTime;

public record GetAllSymptonByPatientResponse(Long id, String name, String description, int severityLevel, LocalDateTime onsetDate, String category, LocalDateTime resolutionDate) {
}

