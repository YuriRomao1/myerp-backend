package com.myproject94.myerp.config;


import com.myproject94.myerp.service.DBServices;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile("test")
public class TestConfig {

    private final DBServices dbServices;

    @PostConstruct
    public void instanciaDB() {
        dbServices.instanciaDB();
    }
}
