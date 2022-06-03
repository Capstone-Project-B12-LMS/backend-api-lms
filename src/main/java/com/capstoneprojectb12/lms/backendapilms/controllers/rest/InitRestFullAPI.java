package com.capstoneprojectb12.lms.backendapilms.controllers.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.capstoneprojectb12.lms.backendapilms.entities.responses.SayHelloInput;
import com.capstoneprojectb12.lms.backendapilms.entities.responses.SayHelloResponse;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiValidation;

@RestController
@RequestMapping(value = { "/restapi" })
public class InitRestFullAPI {
    @GetMapping
    public ResponseEntity<?> init() {
        var response = SayHelloResponse.builder()
                .message("Hi, my name is KELOMPOK B 12")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> init(@RequestBody @Valid SayHelloInput to, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiValidation.getErrorMessages(errors));
        }

        var response = SayHelloResponse
                .builder()
                .message("Hello " + to.getName().toUpperCase() + " how are you?")
                .build();
        return ResponseEntity.ok(response);
    }
}