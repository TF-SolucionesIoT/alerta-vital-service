package com.iot.alertavital.healthtracking.interfaces.rest;


import com.iot.alertavital.healthtracking.domain.model.commands.DeleteDisturbanceCommand;
import com.iot.alertavital.healthtracking.domain.model.commands.DeleteSymptonCommand;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllDisturbancesByPatientIdQuery;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllSymptonsByPatientIdQuery;
import com.iot.alertavital.healthtracking.domain.model.queries.GetAllTreatmentsByPatientIdQuery;
import com.iot.alertavital.healthtracking.domain.services.DisturbanceCommandService;
import com.iot.alertavital.healthtracking.domain.services.DisturbanceQueryService;
import com.iot.alertavital.healthtracking.domain.services.SymptonCommandService;
import com.iot.alertavital.healthtracking.domain.services.SymptonQueryService;
import com.iot.alertavital.healthtracking.domain.services.TreatmentCommandService;
import com.iot.alertavital.healthtracking.domain.services.TreatmentQueryService;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateDisturbanceRequest;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateDisturbanceResponse;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateSymptonRequest;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateSymptonResponse;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateTreatmentRequest;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.CreateTreatmentResponse;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.GetAllDisturbanceByPatientResponse;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.GetAllSymptonByPatientResponse;
import com.iot.alertavital.healthtracking.interfaces.rest.resources.GetAllTreatmentByPatientResponse;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.CreateDisturbanceCommandFromResourceAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.CreateDisturbanceResponseFromEntityAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.CreateSymptonCommandFromResourceAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.CreateSymptonResponseFromEntityAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.CreateTreatmentCommandFromResourceAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.CreateTreatmentResponseFromEntityAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.DisturbanceResourceFromEntityAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.SymptonResourceFromEntityAssembler;
import com.iot.alertavital.healthtracking.interfaces.rest.transform.TreatmentResourceFromEntityAssembler;
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
    private final SymptonCommandService symptonCommandService;
    private final SymptonQueryService symptonQueryService;
    private final TreatmentCommandService treatmentCommandService;
    private final TreatmentQueryService treatmentQueryService;
    public RecordsController(DisturbanceCommandService disturbanceCommandService, DisturbanceQueryService disturbanceQueryService, SymptonCommandService symptonCommandService, SymptonQueryService symptonQueryService, TreatmentCommandService treatmentCommandService, TreatmentQueryService treatmentQueryService) {
        this.disturbanceCommandService = disturbanceCommandService;
        this.disturbanceQueryService = disturbanceQueryService;
        this.symptonCommandService = symptonCommandService;
        this.symptonQueryService = symptonQueryService;
        this.treatmentCommandService = treatmentCommandService;
        this.treatmentQueryService = treatmentQueryService;
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

    @PostMapping("/symptons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "sympton created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<CreateSymptonResponse> createSympton(@RequestBody CreateSymptonRequest request) {
        var createSympton = CreateSymptonCommandFromResourceAssembler.toCommand(request);
        var optionalResponse = symptonCommandService.handle(createSympton);

        if (optionalResponse.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = CreateSymptonResponseFromEntityAssembler.toResponse(optionalResponse.get());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/symptons/all")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "symptons found"),
            @ApiResponse(responseCode = "404", description = "symptons not found")
    })
    public ResponseEntity<List<GetAllSymptonByPatientResponse>> getAllSymptonByPatient() {
        var list = symptonQueryService.handle(new GetAllSymptonsByPatientIdQuery());

        if (list.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var resources = list.stream().map(SymptonResourceFromEntityAssembler::toResource).toList();

        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/symptons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted sympton"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    public ResponseEntity<?> deleteSympton(@RequestBody Long symptonId){
        symptonCommandService.handle(new DeleteSymptonCommand(symptonId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/treatments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "treatment created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<CreateTreatmentResponse> createTreatment(@RequestBody CreateTreatmentRequest request) {
        var createTreatment = CreateTreatmentCommandFromResourceAssembler.toCommand(request);
        var optionalResponse = treatmentCommandService.handle(createTreatment);

        if (optionalResponse.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = CreateTreatmentResponseFromEntityAssembler.toResponse(optionalResponse.get());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/treatments/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "treatments found"),
            @ApiResponse(responseCode = "404", description = "treatments not found")
    })
    public ResponseEntity<List<GetAllTreatmentByPatientResponse>> getAllTreatmentByPatient() {
        var list = treatmentQueryService.handle(new GetAllTreatmentsByPatientIdQuery());

        var resources = list.stream().map(TreatmentResourceFromEntityAssembler::toResource).toList();

        return ResponseEntity.ok(resources);
    }
}
