package com.iot.alertavital.healthtracking.domain.model.commands;

import java.time.LocalDateTime;

public record CreateSymptonCommand(String name, String description, int severity_level, LocalDateTime onset_date, String category, LocalDateTime resolution_date) {}

