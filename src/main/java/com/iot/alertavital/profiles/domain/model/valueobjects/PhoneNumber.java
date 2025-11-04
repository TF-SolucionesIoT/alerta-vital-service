package com.iot.alertavital.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String number) {
}
