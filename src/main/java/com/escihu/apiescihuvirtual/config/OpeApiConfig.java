package com.escihu.apiescihuvirtual.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gestion Escolar API - ESCIHU",
                version = "1.0.0",
                description = "Api con lógica de negocio para la gestión escolar en la ESCIHU"
        )
)
public class OpeApiConfig {
}
