package com.iot.alertavital.profiles.interfaces.REST;


import com.iot.alertavital.profiles.domain.services.ProfileConfigService;
import com.iot.alertavital.profiles.interfaces.REST.resources.ChangePasswordRequest;
import com.iot.alertavital.profiles.interfaces.REST.transform.ProfileConfigCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/me")
public class ProfileConfigController {
    private final ProfileConfigService profileConfigService;

    public ProfileConfigController(ProfileConfigService profileConfigService) {
        this.profileConfigService = profileConfigService;
    }



    @PostMapping("/change-password")
    public ResponseEntity<Map<String,String>> changePassword(@RequestBody ChangePasswordRequest request){
        profileConfigService.handle(ProfileConfigCommandFromResourceAssembler.toCommand(request));
        return ResponseEntity.ok(Map.of("message","Password has been updated"));


    }

}


