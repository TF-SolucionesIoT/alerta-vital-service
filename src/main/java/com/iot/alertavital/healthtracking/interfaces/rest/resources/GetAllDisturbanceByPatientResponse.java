package com.iot.alertavital.healthtracking.interfaces.rest.resources;

import java.time.LocalDate;

public record GetAllDisturbanceByPatientResponse(Long id, String name, String description, int severityLevel, LocalDate onsetDate) {
}
