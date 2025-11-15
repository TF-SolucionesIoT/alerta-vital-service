package com.iot.alertavital.emergencymanagement.interfaces.rest.resources;

public record ContactResponse(Long id,
                              String name,
                              String connection,
                              String phoneNumber,
                              Long patientId) {
}
