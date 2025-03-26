package com.myproject94.myerp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("test")
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sua API")
                        .version("1.0")
                        .description("Descrição detalhada da sua API")
                        .contact(new Contact()
                                .name("yuri")
                                .email("yuri@email.com")
                        )
                );
    }
}
