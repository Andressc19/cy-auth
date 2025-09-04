package co.com.pragma.api.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "CY Autenticación",
		version = "1.3.0",
		description = "Microservicio para el manejo de usuarios",
		contact = @Contact(name = "Andres Camperos")
	),
	security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(name = "bearerAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, description = "JWT Bearer authentication", bearerFormat = "JWT")
public class SwaggerConfig {
}
