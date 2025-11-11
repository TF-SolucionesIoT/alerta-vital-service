package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.entities.PatientInviteCode;

public interface InviteCodeService {
    PatientInviteCode generateCode();
    String useCode(String code);
}
