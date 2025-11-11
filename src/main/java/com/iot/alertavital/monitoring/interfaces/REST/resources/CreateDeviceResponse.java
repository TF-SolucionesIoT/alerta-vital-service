package com.iot.alertavital.monitoring.interfaces.REST.resources;

public record CreateDeviceResponse(Long id, String deviceId, Boolean status, String patientName) {
}
