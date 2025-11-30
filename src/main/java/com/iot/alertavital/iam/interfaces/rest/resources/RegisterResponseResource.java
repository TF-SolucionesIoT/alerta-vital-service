package com.iot.alertavital.iam.interfaces.rest.resources;

public record RegisterResponseResource(String accessToken, String refreshToken, Long userId, String userType, String fullName) {
}
