package com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils;

public class JSON {
    public static final String create(Object object) {
        return Constant.getMapper().valueToTree(object).toString();
    }
}
