package com.myproject94.myerp.config;

import com.myproject94.myerp.service.DBServices;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    private final DBServices dbServices;
    private final String ddlAuto;

    @Autowired
    public DevConfig(DBServices dbServices,
                     @Value("${spring.jpa.hibernate.ddl-auto}") String ddlAuto) {
        this.dbServices = dbServices;
        this.ddlAuto    = ddlAuto;
    }
    @PostConstruct
    public void instanciaDB() {
        if (ddlAuto != null && ddlAuto.startsWith("create")) {
            dbServices.instanciaDB();
        }
    }
}
