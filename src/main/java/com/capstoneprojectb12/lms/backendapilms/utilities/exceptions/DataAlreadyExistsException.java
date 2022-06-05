package com.capstoneprojectb12.lms.backendapilms.utilities.exceptions;

import org.springframework.stereotype.Component;

@Component
public class DataAlreadyExistsException extends RuntimeException {
    public DataAlreadyExistsException() {
        super("Data already exists! :");
    }

}
