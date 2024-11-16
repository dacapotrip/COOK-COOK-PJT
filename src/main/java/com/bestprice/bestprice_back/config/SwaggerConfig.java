package com.bestprice.bestprice_back.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Example API Docs", description = "Description", version = "v1"))
@Configuration
public class SwaggerConfig {

        private static final String BEARER_SCHEME = "bearer";
        private static final String JWT_FORMAT = "JWT";

        @Bean
        public OpenAPI openAPI() {
                String securitySchemeName = "JWT";

                // Security Requirement 추가
                SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);

                // Components에 SecurityScheme 추가
                Components components = new Components()
                                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme(BEARER_SCHEME) // "bearer"로 지정
                                                .bearerFormat(JWT_FORMAT)); // "JWT"로 포맷 지정

                // OpenAPI 반환
                return new OpenAPI()
                                .addSecurityItem(securityRequirement)
                                .components(components);
        }

}
