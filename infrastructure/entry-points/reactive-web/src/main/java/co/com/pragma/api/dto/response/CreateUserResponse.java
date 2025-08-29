package co.com.pragma.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserResponse(
      String firstName,
      String lastName,
      LocalDate birthDate,
	  String identificationNumber,
      String address,
      String phone,
      String email,
      BigDecimal baseSalary,
	  Short roleId
) {}
