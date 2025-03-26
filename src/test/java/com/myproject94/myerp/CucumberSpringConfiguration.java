package com.myproject94.myerp;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CucumberSpringConfiguration {
}
