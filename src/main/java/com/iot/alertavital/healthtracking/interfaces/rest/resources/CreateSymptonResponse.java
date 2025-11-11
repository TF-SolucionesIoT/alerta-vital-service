package com.iot.alertavital.healthtracking.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateSymptonResponse(String name, String description, int severity_level, LocalDateTime onset_date, String category, LocalDateTime resolution_date) {
}

