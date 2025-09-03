package co.com.pragma.api.dto.request;

import co.com.pragma.api.decorators.ValidBirthDate;
import co.com.pragma.api.decorators.ValidIdentificationNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Request para crear un usuario")
public record CreateUserRequest(

      @NotBlank(message = "Debe ingresar un nombre válido")
      @Schema(description = "Nombre del usuario", example = "John")
      String firstName,

      @NotBlank(message = "Debe ingresar un apellido válido")
      @Schema(description = "Apellido del usuario", example = "Doe")
      String lastName,
      
      @NotBlank(message = "Debe ingresar un número de identificación válido")
      @Schema(description = "Número de identificación", example = "1234567880")
      @ValidIdentificationNumber
      String identificationNumber,
      
      @ValidBirthDate
      @Schema(description = "Fecha de nacimiento", example = "1995-08-24")
      String birthDate,

      @NotBlank(message = "Debe ingresar una dirección")
      @Schema(description = "Dirección del usuario", example = "Cra 123 #45-67")
      String address,

      @NotBlank(message = "Debe ingresar un teléfono")
      @Schema(description = "Teléfono del usuario", example = "3001234567")
      String phone,

      @NotBlank(message = "Debe ingresar un correo electrónico")
      @Email(message = "Debe ingresar un correo electrónico válido")
      @Schema(description = "Correo electrónico", example = "johndoe@mail.com")
      String email,

      @NotNull(message = "Debe ingresar un salario base")
      @DecimalMin(value = "0", message = "El salario mínimo es 0")
      @DecimalMax(value = "15000000", message = "El salario máximo es 15000000")
      @Schema(description = "Salario base", example = "2500000")
      BigDecimal baseSalary,
      
      @NotNull(message = "Debe ingresar un rol valido")
      @Schema(description = "Rol", example="1")
      Short role
) {}