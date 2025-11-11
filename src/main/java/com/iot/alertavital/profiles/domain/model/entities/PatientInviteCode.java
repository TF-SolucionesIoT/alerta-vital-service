package com.iot.alertavital.profiles.domain.model.entities;


import com.iot.alertavital.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class PatientInviteCode extends AuditableModel {

    @Column(nullable = false)
    private Long patientId; // referencia al agregado Patient

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Boolean used = false;

    public PatientInviteCode() {}


}
