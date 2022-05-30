package com.capstoneprojectb12.lms.backendapilms.controller.hello;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Tag(value = "helloMutation")
public class HelloMutationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sayHello() {

    }
}
