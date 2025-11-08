package com.iot.alertavital.healthtracking.application.internal;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Sympton;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllSymptonsByPatientIdQuery;
import com.iot.alertavital.healthtracking.domain.services.SymptonQueryService;
import com.iot.alertavital.healthtracking.infrastructure.repositories.SymptonRepository;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptonQueryServiceImpl implements SymptonQueryService {

    SymptonRepository symptonRepository;
    AuthenticatedUserProvider authenticatedUserProvider;
    PatientRepository patientRepository;

    public SymptonQueryServiceImpl(SymptonRepository _symptonRepository, AuthenticatedUserProvider _authenticatedUserProvider, PatientRepository _patientRepository)
    {
        symptonRepository = _symptonRepository;
        authenticatedUserProvider = _authenticatedUserProvider;
        patientRepository = _patientRepository;


    }



    @Override
    public List<Sympton> handle(GetAllSymptonsByPatientIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        return symptonRepository.findAllByPatient_User_IdOrderByOnsetDateDesc(userId);
    }
}

