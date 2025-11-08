package com.iot.alertavital.healthtracking.application.internal;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Treatments;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllTreatmentsByPatientIdQuery;
import com.iot.alertavital.healthtracking.domain.services.TreatmentQueryService;
import com.iot.alertavital.healthtracking.infrastructure.repositories.TreatmentsRepository;
import com.iot.alertavital.iam.infrastructure.security.AuthenticatedUserProvider;
import com.iot.alertavital.profiles.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentQueryServiceImpl implements TreatmentQueryService {

    TreatmentsRepository treatmentsRepository;
    AuthenticatedUserProvider authenticatedUserProvider;
    PatientRepository patientRepository;

    public TreatmentQueryServiceImpl(TreatmentsRepository _treatmentsRepository, AuthenticatedUserProvider _authenticatedUserProvider, PatientRepository _patientRepository)
    {
        treatmentsRepository = _treatmentsRepository;
        authenticatedUserProvider = _authenticatedUserProvider;
        patientRepository = _patientRepository;
    }

    @Override
    public List<Treatments> handle(GetAllTreatmentsByPatientIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        return treatmentsRepository.findAllByPatient_User_IdOrderByStartDateDesc(userId);
    }
}

