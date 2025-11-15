package com.iot.alertavital.healthtracking.interfaces.rest.resources;

import java.util.List;

public record PatientHealthRecordsResponse(
        Long patientId,
        String patientName,
        List<GetAllDisturbanceByPatientResponse> disturbances,
        List<GetAllSymptonByPatientResponse> symptons,
        List<GetAllTreatmentByPatientResponse> treatments
) {
}
