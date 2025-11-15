package com.iot.alertavital.monitoring.interfaces.REST.resources;

public record VitalSignRequest(String deviceId, int bpm, int spo2) {}