package com.iot.alertavital.emergencymanagement.interfaces.rest.resources;

public record CreateContactRequest(String name,
                                   String connection,
                                   String phoneNumber,
                                   Long patientId) {
}
