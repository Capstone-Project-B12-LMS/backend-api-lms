package com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils;

import org.springframework.test.web.servlet.MvcResult;

public class JSON {
    public static final String create(Object object) {
        return Constant.getMapper().valueToTree(object).toString();
    }

    public static String getToken(MvcResult result) throws Exception {
        var contentString = result.getResponse().getContentAsString();
        var token = contentString.substring(
                contentString.indexOf("eyJhbGciOiJIUzI1NiJ9"),
                contentString.indexOf("\",\""));
        return token;
    }
}
