package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes.material;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = { "/restapi/class/material" })
public class ClassMaterialController {
    @PostMapping
    public ResponseEntity<?> save() {
        return null;
    }
}
