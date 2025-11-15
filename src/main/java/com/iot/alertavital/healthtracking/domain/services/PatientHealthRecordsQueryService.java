package com.iot.alertavital.healthtracking.domain.services;

import com.iot.alertavital.healthtracking.domain.model.queries.GetPatientHealthRecordsByPatientIdQuery;

public interface PatientHealthRecordsQueryService {
    Object handle(GetPatientHealthRecordsByPatientIdQuery query);
}
