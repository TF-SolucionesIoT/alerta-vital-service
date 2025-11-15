package com.iot.alertavital.profiles.domain.services;

import com.iot.alertavital.profiles.domain.model.entities.PatientInviteCode;

import java.util.List;
import java.util.Map;

public interface InviteCodeService {
    PatientInviteCode generateCode();
    String useCode(String code);
    List<Map<String, Object>> getLinkedPatients();
}
