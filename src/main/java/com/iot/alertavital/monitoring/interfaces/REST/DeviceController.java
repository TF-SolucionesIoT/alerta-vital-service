package com.iot.alertavital.monitoring.interfaces.REST;


import com.iot.alertavital.monitoring.domain.model.queries.GetAllReadingByDescQuery;
import com.iot.alertavital.monitoring.domain.services.DeviceCommandService;
import com.iot.alertavital.monitoring.domain.services.ReadingDeviceQueryService;
import com.iot.alertavital.monitoring.infrastructure.repositories.DeviceRepository;
import com.iot.alertavital.monitoring.interfaces.REST.resources.CreateDeviceRequest;
import com.iot.alertavital.monitoring.interfaces.REST.resources.CreateDeviceResponse;
import com.iot.alertavital.monitoring.interfaces.REST.resources.GetAllReadingByDescResponse;
import com.iot.alertavital.monitoring.interfaces.REST.transform.CreateDeviceCommandFromResourceAssembler;
import com.iot.alertavital.monitoring.interfaces.REST.transform.DeviceResourceFromEntityAssembler;
import com.iot.alertavital.monitoring.interfaces.REST.transform.ReadingDeviceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceController {
    private final DeviceCommandService deviceCommandService;
    private ReadingDeviceQueryService readingDeviceQueryService;


    public DeviceController( DeviceCommandService deviceCommandService, ReadingDeviceQueryService readingDeviceQueryService) {
        this.deviceCommandService = deviceCommandService;
        this.readingDeviceQueryService = readingDeviceQueryService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "device created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<CreateDeviceResponse> createDeviceResponseResponseEntity(@RequestBody CreateDeviceRequest createDeviceRequest) {
        var createDevice = CreateDeviceCommandFromResourceAssembler.FromResource(createDeviceRequest);
        var optionalResponse = deviceCommandService.handle(createDevice);

        if (optionalResponse.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var response = DeviceResourceFromEntityAssembler.fromEntity(optionalResponse.get());
        return ResponseEntity.status(201).body(response);

    }

    @GetMapping("/readings/all")
    public ResponseEntity<List<GetAllReadingByDescResponse>> getAllReadingByDescResponse() {
        var list = readingDeviceQueryService.handle(new GetAllReadingByDescQuery());

        if (list.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var resource = list.stream().map(ReadingDeviceResourceFromEntityAssembler::toResource).toList();
        return ResponseEntity.status(200).body(resource);

    }


}
