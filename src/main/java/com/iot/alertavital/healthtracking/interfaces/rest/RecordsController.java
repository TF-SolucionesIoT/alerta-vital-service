package com.iot.alertavital.healthtracking.interfaces.rest;


import com.iot.alertavital.healthtracking.domain.model.commands.DeleteDisturbanceCommand;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllDisturbancesByPatientIdQuery;
import com.iot.alertavital.healthtracking.domain.services.DisturbanceCommandService;
import com.iot.alertavital.healthtracking.domain.services.DisturbanceQueryService;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateDisturbanceRequest;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateDisturbanceResponse;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.GetAllDisturbanceByPatientResponse;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.CreateDisturbanceCommandFromResourceAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.CreateDisturbanceResponseFromEntityAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.DisturbanceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordsController {
    private final DisturbanceCommandService disturbanceCommandService;
    private final DisturbanceQueryService disturbanceQueryService;
    public RecordsController(DisturbanceCommandService disturbanceCommandService, DisturbanceQueryService disturbanceQueryService) {
        this.disturbanceCommandService = disturbanceCommandService;
        this.disturbanceQueryService = disturbanceQueryService;
    }

    @PostMapping("/disturbances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "disturbance created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<CreateDisturbanceResponse> createDisturbance(@RequestBody CreateDisturbanceRequest request) {
        var createDisturbance = CreateDisturbanceCommandFromResourceAssembler.toCommand(request);
        var optionalResponse = disturbanceCommandService.handle(createDisturbance);

        if (optionalResponse.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = CreateDisturbanceResponseFromEntityAssembler.toResponse(optionalResponse.get());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/disturbances/all")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "disturbances found"),
            @ApiResponse(responseCode = "404", description = "disturbances not found")
    })
    public ResponseEntity<List<GetAllDisturbanceByPatientResponse>> getAllDisturbanceByPatient() {
        var list = disturbanceQueryService.handle(new GetAllDisturbancesByPatientIdQuery());

        if (list.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var resources = list.stream().map(DisturbanceResourceFromEntityAssembler::toResource).toList();

        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/disturbances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted disturbance"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    public ResponseEntity<?> deleteDisturbance(@RequestBody Long disturbanceId){
        disturbanceCommandService.handle(new DeleteDisturbanceCommand(disturbanceId));
        return ResponseEntity.noContent().build();
    }
}
