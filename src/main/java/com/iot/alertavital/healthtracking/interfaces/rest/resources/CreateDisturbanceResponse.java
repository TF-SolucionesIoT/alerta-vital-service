package com.iot.alertavital.healthtracking.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateDisturbanceResponse(String name, String description, int severity_level, LocalDate onset_date) {
}
