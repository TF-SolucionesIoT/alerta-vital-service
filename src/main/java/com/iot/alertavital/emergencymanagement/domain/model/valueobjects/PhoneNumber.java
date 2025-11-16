package com.iot.alertavital.emergencymanagement.domain.model.valueobjects;

public record PhoneNumber(String phoneNumber) {

    private static final String PERU_CELL_REGEX = "^9\\d{8}$";

    public PhoneNumber {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
        }

        if (!phoneNumber.matches(PERU_CELL_REGEX)) {
            throw new IllegalArgumentException("Phone number invalid. It must have 9 digits and start with the digit 9.");
        }
    }

}
