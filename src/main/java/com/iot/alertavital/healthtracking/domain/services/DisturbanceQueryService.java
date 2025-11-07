package com.iot.alertavital.healthtracking.domain.services;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Disturbance;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllDisturbancesByPatientIdQuery;

import java.util.List;

public interface DisturbanceQueryService {
    List<Disturbance> handle(GetAllDisturbancesByPatientIdQuery query);

}
