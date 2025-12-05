package com.iot.alertavital.monitoring.interfaces.REST;


import com.iot.alertavital.monitoring.domain.model.commands.CreateAlertCommand;
import com.iot.alertavital.monitoring.domain.model.queries.GetAllAlertsByDescQuery;
import com.iot.alertavital.monitoring.domain.model.queries.GetAllReadingByDescQuery;
import com.iot.alertavital.monitoring.domain.services.DeviceCommandService;
import com.iot.alertavital.monitoring.domain.services.ReadingDeviceCommandService;
import com.iot.alertavital.monitoring.domain.services.ReadingDeviceQueryService;
import com.iot.alertavital.monitoring.interfaces.REST.resources.*;
import com.iot.alertavital.monitoring.interfaces.REST.transform.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/device")
public class DeviceController {
    private final DeviceCommandService deviceCommandService;
    private final ReadingDeviceQueryService readingDeviceQueryService;
    private final ReadingDeviceCommandService readingDeviceCommandService;


    public DeviceController(DeviceCommandService deviceCommandService, ReadingDeviceQueryService readingDeviceQueryService, ReadingDeviceCommandService readingDeviceCommandService) {
        this.deviceCommandService = deviceCommandService;
        this.readingDeviceQueryService = readingDeviceQueryService;
        this.readingDeviceCommandService = readingDeviceCommandService;
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
            return ResponseEntity.noContent().build();
        }

        var resource = list.stream().map(ReadingDeviceResourceFromEntityAssembler::toResource).toList();
        return ResponseEntity.ok(resource);

    }

    @PostMapping("/readings")
    public ResponseEntity<Void> createReadingDevice(
            @RequestBody CreateReadingDeviceRequest request) {

        var command = CreateReadingDeviceCommandFromResourceAssembler.toCommand(request);

        var optionalResult = readingDeviceCommandService.handle(command);

        if (optionalResult.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Solo devolver 201 Created sin body
        return ResponseEntity.status(201).build();
    }


    @GetMapping("/reading-alerts/all")
    public ResponseEntity<List<GetAllAlertsByDescResponse>> getAllReadingAlertsByDescResponse() {
        var list = readingDeviceQueryService.handle(new GetAllAlertsByDescQuery());
        if (list.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var resource = list.stream().map(ReadingAlertResourceFromEntityAssembler::toResource).
                toList();
        return ResponseEntity.status(200).body(resource);
    }


    @PostMapping("/emmit-alert")
    public ResponseEntity<Optional<?>> emmitAlert(@RequestBody CreateAlertRequest request) {
        var optionalResponse = deviceCommandService.handle(new CreateAlertCommand(request.deviceId(), request.dateTime()));
        if (optionalResponse.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        //map to json response
        var response = optionalResponse.get();
        return ResponseEntity.status(201).body(Optional.of(response));
    }






}
