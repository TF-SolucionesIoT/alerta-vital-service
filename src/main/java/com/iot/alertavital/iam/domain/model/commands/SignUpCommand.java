package com.iot.alertavital.iam.domain.model.commands;

public record SignUpCommand(String firstName, String lastName, String email, String gender, String username, String password) {
    public SignUpCommand {
        if (firstName.isEmpty() || firstName.length() < 3) {
            throw new IllegalArgumentException("First name must be at least 3 characters");
        }
        if (lastName.isEmpty() || lastName.length() < 3) {
            throw new IllegalArgumentException("Last name must be at least 3 characters");
        }
        if (email.isEmpty() || email.length() < 3) {
            throw new IllegalArgumentException("Email must be at least 3 characters");
        }
        if (gender.isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }
        if (username.isEmpty() || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

    }
}