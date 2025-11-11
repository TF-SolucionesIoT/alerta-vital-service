package com.iot.alertavital.healthtracking.domain.model.aggregates;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateSymptonCommand;
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
public class Sympton extends AuditableAbstractAggregateRoot<Sympton> {

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
    private LocalDateTime onsetDate;

    @PastOrPresent
    private LocalDateTime resolutionDate;

    @NotBlank
    @Size(max = 30)
    private String category;

    @Setter
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)  // Clave foránea en la tabla symptons
    private Patient patient;

    public Sympton() {}

    public Sympton(String name, String description, int severity_level, LocalDateTime onset_date, String category, Patient patient) {
        this.name = name;
        this.description = description;
        this.severityLevel = severity_level;
        this.onsetDate = onset_date;
        this.category = category;
        this.patient = patient;
    }

    public Sympton(String name, String description, int severity_level, LocalDateTime onset_date, LocalDateTime resolution_date, String category, Patient patient) {
        this.name = name;
        this.description = description;
        this.severityLevel = severity_level;
        this.onsetDate = onset_date;
        this.resolutionDate = resolution_date;
        this.category = category;
        this.patient = patient;
    }


}