package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.entities.PatientInviteCode;

import java.util.Map;

public interface InviteCodeService {
    PatientInviteCode generateCode();
    Map<String, Object> useCode(String code);
}
