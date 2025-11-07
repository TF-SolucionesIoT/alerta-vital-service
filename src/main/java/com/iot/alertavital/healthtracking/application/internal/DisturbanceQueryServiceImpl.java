package com.iot.alertavital.healthtracking.application.internal;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Disturbance;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllDisturbancesByPatientIdQuery;
import com.iot.alertavital.healthtracking.domain.services.DisturbanceQueryService;
import com.iot.alertavital.healthtracking.infrastructure.repositories.DisturbanceRepository;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisturbanceQueryServiceImpl implements DisturbanceQueryService {

    DisturbanceRepository disturbanceRepository;
    AuthenticatedUserProvider authenticatedUserProvider;
    PatientRepository patientRepository;

    public DisturbanceQueryServiceImpl(DisturbanceRepository _disturbanceRepository, AuthenticatedUserProvider _authenticatedUserProvider, PatientRepository _patientRepository)
    {
        disturbanceRepository = _disturbanceRepository;
        authenticatedUserProvider = _authenticatedUserProvider;
        patientRepository = _patientRepository;


    }



    @Override
    public List<Disturbance> handle(GetAllDisturbancesByPatientIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        return disturbanceRepository.findAllByPatient_User_IdOrderByOnsetDateDesc(userId);
    }
}
