package com.iot.alertavital.emergencymanagement.interfaces.rest;

import com.iot.alertavital.emergencymanagement.domain.model.commands.DeleteContactCommand;
import com.iot.alertavital.emergencymanagement.domain.model.queries.GetAllContactsByPatientIdQuery;
import com.iot.alertavital.emergencymanagement.domain.services.ContactCommandService;
import com.iot.alertavital.emergencymanagement.domain.services.ContactQueryService;
import com.iot.alertavital.emergencymanagement.interfaces.rest.resources.ContactResponse;
import com.iot.alertavital.emergencymanagement.interfaces.rest.resources.CreateContactRequest;
import com.iot.alertavital.emergencymanagement.interfaces.rest.resources.UpdateContactRequest;
import com.iot.alertavital.emergencymanagement.interfaces.rest.transform.ContactResponseFromEntityAssembler;
import com.iot.alertavital.emergencymanagement.interfaces.rest.transform.CreateContactCommandFromResourceAssembler;
import com.iot.alertavital.emergencymanagement.interfaces.rest.transform.UpdateContactCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {
    private final ContactQueryService contactQueryService;
    private final ContactCommandService contactCommandService;

    public ContactsController(ContactQueryService contactQueryService, ContactCommandService contactCommandService) {
        this.contactQueryService = contactQueryService;
        this.contactCommandService = contactCommandService;
    }

    @PostMapping("/emergencies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact created"),
            @ApiResponse(responseCode = "404", description = "Invalid input")
    })
    public ResponseEntity<?> createdContact(@RequestBody CreateContactRequest request) {
        var contact = CreateContactCommandFromResourceAssembler.toCommand(request);
        var response = contactCommandService.handle(contact);
        return ResponseEntity.ok(ContactResponseFromEntityAssembler.toResource(response.get()));
    }

    @GetMapping("/emergencies/patient/{patientId}/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts found"),
            @ApiResponse(responseCode = "404", description = "Contacts not found")
    })
    public ResponseEntity<List<ContactResponse>> getAllContactsByPatientId(@PathVariable Long patientId) {
        var list = contactQueryService.handle(new GetAllContactsByPatientIdQuery(patientId));
        if(list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var response = list.stream().map(ContactResponseFromEntityAssembler::toResource).toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/emergencies/contact/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted contact"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        contactCommandService.handle(new DeleteContactCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/emergencies/contact/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact updated"),
            @ApiResponse(responseCode = "404", description = "Invalid input")
    })
    public ResponseEntity<ContactResponse> updateContact(@PathVariable Long id,
                                                         @RequestBody UpdateContactRequest request) {
        var contact = UpdateContactCommandFromResourceAssembler.toCommand(id, request);
        var response = contactCommandService.handle(contact);
        return response
                .map(r -> ResponseEntity.ok(ContactResponseFromEntityAssembler.toResource(response.get())))
                .orElse(ResponseEntity.notFound().build());
    }
}
