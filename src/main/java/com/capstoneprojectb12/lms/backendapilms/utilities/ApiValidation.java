package com.capstoneprojectb12.lms.backendapilms.utilities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;

public class ApiValidation {
    public static Map<String, Object> getErrorMessages(Errors errors) {
        final var message = new HashMap<String, Object>();
        for (var error : errors.getFieldErrors()) {
            message.put(error.getField(), error.getDefaultMessage());
        }
        return message;
    }
}
