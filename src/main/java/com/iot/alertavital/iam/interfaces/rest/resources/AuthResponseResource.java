package com.iot.alertavital.iam.interfaces.rest.resources;

public record AuthResponseResource(String accessToken, String refreshToken, Long userId, String typeOfUser, String fullName) {
}
