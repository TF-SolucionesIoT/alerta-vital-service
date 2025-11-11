package com.iot.alertavital.healthtracking.domain.services;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Treatments;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllTreatmentsByPatientIdQuery;

import java.util.List;

public interface TreatmentQueryService {
    List<Treatments> handle(GetAllTreatmentsByPatientIdQuery query);
}

