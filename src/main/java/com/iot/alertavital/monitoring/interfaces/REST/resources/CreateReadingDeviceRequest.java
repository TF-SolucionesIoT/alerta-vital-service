package com.iot.alertavital.monitoring.interfaces.REST.resources;

public record CreateReadingDeviceRequest(Integer spO2, Integer bpm, String deviceId, Integer bpDiastolic, Integer bpSystolic){
}
