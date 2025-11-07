package com.iot.alertavital.healthtracking.domain.model.aggregates;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateDisturbanceCommand;
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

import java.time.LocalDate;

@Getter
@Entity
public class Disturbance extends AuditableAbstractAggregateRoot<Disturbance> {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String description;

    @NotNull
    private int severityLevel; // 1 - 5

    @NotNull
    @PastOrPresent
    private LocalDate onsetDate;

    @Setter
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)  // Clave foránea en la tabla disturbances
    private Patient patient;

    public Disturbance() {}

    public Disturbance(String name, String description, int severity_level, LocalDate onset_date, Patient patient) {
        this.name = name;
        this.description = description;
        this.severityLevel = severity_level;
        this.onsetDate = onset_date;
        this.patient = patient;
    }


}
