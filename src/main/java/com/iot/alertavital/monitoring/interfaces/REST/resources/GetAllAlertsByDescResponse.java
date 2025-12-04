package com.iot.alertavital.monitoring.interfaces.REST.resources;

import java.time.Instant;

public record GetAllAlertsByDescResponse(Long id, String deviceId, Instant timestamp){
}
