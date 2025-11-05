package com.iot.alertavital.healthtracking.domain.model.entities;

import com.iot.alertavital.profiles.domain.model.aggregates.Patient;
import com.iot.alertavital.healthtracking.domain.model.commands.CreateDisturbanceCommand;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Disturbances extends AuditableAbstractAggregateRoot<Disturbances> {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String description;

    @NotBlank
    private int severity_level; // 1 - 5

    @NotBlank
    private LocalDate onset_date;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "patient_id")  // Clave foránea en la tabla disturbances
    private Patient patient;

    public Disturbances() {}

    public Disturbances(String name, String description, int severity_level, LocalDate onset_date) {
        this.name = name;
        this.description = description;
        this.severity_level = severity_level;
        this.onset_date = onset_date;
    }

    public Disturbances(CreateDisturbanceCommand command) {
        this.name = command.name();
        this.description = command.description();
        this.severity_level = command.severity_level();
        this.onset_date = command.onset_date();
    }

}
