package co.com.pragma.api.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
      info = @Info(
            title = "CY API",
            version = "1.0.0",
            description = "API for managing personal loan application",
            contact = @Contact(name = "Andres Camperos")
      )
)
public class SwaggerConfig {
}
