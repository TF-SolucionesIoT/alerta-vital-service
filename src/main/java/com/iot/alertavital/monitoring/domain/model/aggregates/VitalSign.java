package com.iot.alertavital.monitoring.domain.model.aggregates;

import com.iot.alertavital.monitoring.domain.model.valueobjects.Bpm;
import com.iot.alertavital.monitoring.domain.model.valueobjects.Spo2;
import com.iot.alertavital.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Entity
public class VitalSign extends AuditableAbstractAggregateRoot<VitalSign> {

    @NotBlank
    private String deviceId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "pulse_value")),
    })
    private Bpm bpm;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "spo2_value")),
    })
    private Spo2 spo2;

    public VitalSign() {}

    public VitalSign(Bpm bpm, Spo2 spo2) {
        this.bpm = bpm;
        this.spo2 = spo2;

    }
}
