package com.iot.alertavital.profiles.domain.model.aggregates;

import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "caregiver_patient_access")
public class CaregiverPatientAccess extends AuditableAbstractAggregateRoot<CaregiverPatientAccess> {

    @Column(nullable = false)
    private Long caregiverId;

    @Column(nullable = false)
    private Long patientId;

    private String accessLevel = "read";

    private LocalDateTime grantedDate = LocalDateTime.now();

    public CaregiverPatientAccess() {}
    // Getters y setters
}