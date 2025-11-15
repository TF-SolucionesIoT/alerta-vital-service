package com.iot.alertavital.monitoring.domain.model.valueobjects;

public record Bpm(int value) {
    public Bpm{
        if (value < 0 || value > 250)
            throw new IllegalArgumentException("Bpm value must be between 20 and 100");
    }
}
