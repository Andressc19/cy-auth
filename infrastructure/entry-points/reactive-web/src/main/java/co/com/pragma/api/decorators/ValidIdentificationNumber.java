package co.com.pragma.api.decorators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdentificationValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentificationNumber {
	String message() default "Documento de identidad inválido, debe ser un número.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}
