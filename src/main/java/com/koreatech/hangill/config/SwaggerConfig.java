package com.koreatech.hangill.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "HanGill API 명세서",
                description = "API for HanGill App.",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
}
