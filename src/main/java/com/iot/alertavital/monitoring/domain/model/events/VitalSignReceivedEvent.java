package com.iot.alertavital.monitoring.domain.model.events;

public record VitalSignReceivedEvent(Long vitalSignId, int bpm, int spo2) {}
