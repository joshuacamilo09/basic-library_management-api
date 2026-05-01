package com.library.library_api.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info (
                title = "Library API",
                version = "V1",
                description = "Management API for libraries",
                contact = @Contact(
                        name = "Joshua",
                        email = "joshua@email.com"
                )
        )
)
public class OpenAPIConfig {}
