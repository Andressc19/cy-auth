package co.com.pragma.api.dto.request;

import co.com.pragma.api.decorators.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticateUserRequest(
	
	@NotBlank(message = "Debe ingresar un correo electrónico")
	@Email(message = "Debe ingresar un correo electrónico válido")
	@Schema(description = "Correo electrónico", example = "johndoe@mail.com")
	String email,
	
	@NotBlank
	@ValidPassword
	@Schema(description = "Contraseña el usuario", example = "Prueba123!")
	String password
) {}
