package com.capstoneprojectb12;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Beans {
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static BCryptPasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }
}
