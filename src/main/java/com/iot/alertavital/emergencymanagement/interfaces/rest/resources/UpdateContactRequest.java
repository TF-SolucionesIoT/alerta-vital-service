package com.iot.alertavital.emergencymanagement.interfaces.rest.resources;

public record UpdateContactRequest(String name,
                                   String connection,
                                   String phoneNumber) {
}
