package com.iot.alertavital.emergencymanagement.domain.model.queries;

public record GetAllContactsByPatientIdQuery(Long patientId) {

    public GetAllContactsByPatientIdQuery {
        if(patientId == null || patientId <= 0) {
            throw new IllegalArgumentException("Patient ID cannot be null or lower than one");
        }
    }

}
