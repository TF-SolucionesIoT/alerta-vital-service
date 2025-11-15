package com.iot.alertavital.healthtracking.interfaces.rest.transform;

import com.iot.alertavital.healthtracking.domain.model.aggregates.Disturbance;
import com.iot.alertavital.healthtracking.domain.model.aggregates.Sympton;
import com.iot.alertavital.healthtracking.domain.model.aggregates.Treatments;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.PatientHealthRecordsResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PatientHealthRecordsAssembler {

    public static PatientHealthRecordsResponse toResponse(Map<String, Object> records) {
        return new PatientHealthRecordsResponse(
                (Long) records.get("patientId"),
                (String) records.get("patientName"),
                ((List<?>) records.get("disturbances")).stream()
                        .map(d -> DisturbanceResourceFromEntityAssembler.toResource((Disturbance) d))
                        .collect(Collectors.toList()),
                ((List<?>) records.get("symptons")).stream()
                        .map(s -> SymptonResourceFromEntityAssembler.toResource((Sympton) s))
                        .collect(Collectors.toList()),
                ((List<?>) records.get("treatments")).stream()
                        .map(t -> TreatmentResourceFromEntityAssembler.toResource((Treatments) t))
                        .collect(Collectors.toList())
        );
    }
}
