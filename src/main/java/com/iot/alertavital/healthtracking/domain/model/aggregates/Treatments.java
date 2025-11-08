package com.iot.alertavital.healthtracking.domain.model.aggregates;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Treatments extends AuditableAbstractAggregateRoot<Treatments> {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(max = 100)
    private String frequency;

    @NotBlank
    @Size(max = 100)
    private String dosage;

    @NotNull
    @PastOrPresent
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    private Boolean isActive;

    @Setter
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public Treatments() {}

    public Treatments(String name, String description, String frequency, String dosage, LocalDateTime startDate, LocalDateTime endDate, Boolean isActive, Patient patient) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.patient = patient;
    }

}