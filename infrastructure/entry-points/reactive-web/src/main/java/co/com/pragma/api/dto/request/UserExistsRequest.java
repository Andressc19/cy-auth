package co.com.pragma.api.dto.request;

import co.com.pragma.api.decorators.ValidIdentificationNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request para verificar si existe un usuario por email e identificación")
public record UserExistsRequest(
	
	@Schema(description = "Email del usuario", example = "test@pragma.com")
	@NotNull(message = "El email no puede ser nulo")
	@Email(message = "El email no tiene un formato válido")
	String email,
	
	@Schema(description = "Número de identificación del usuario", example = "123456789")
	@ValidIdentificationNumber
	String identificationNumber
) {}