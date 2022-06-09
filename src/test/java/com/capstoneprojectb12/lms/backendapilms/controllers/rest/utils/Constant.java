package com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constant {
    public static final String BASE_URL = "/restapi/v1";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
