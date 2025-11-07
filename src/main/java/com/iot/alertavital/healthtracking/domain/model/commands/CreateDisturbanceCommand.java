package com.iot.alertavital.healthtracking.domain.model.commands;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;

import java.time.LocalDate;

public record CreateDisturbanceCommand(String name, String description, int severity_level, LocalDate onset_date) {}
