package com.iot.alertavital.healthtracking.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateDisturbanceRequest(String name, String description, int severity_level, LocalDate onset_date) {
}
