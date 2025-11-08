package com.iot.alertavital.healthtracking.domain.services;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Sympton;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllSymptonsByPatientIdQuery;

import java.util.List;

public interface SymptonQueryService {
    List<Sympton> handle(GetAllSymptonsByPatientIdQuery query);

}

