package co.com.pragma.api.decorators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BirthDateValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthDate {
    String message() default "Fecha inválida. Debe estar en formato yyyy-MM-dd y ser anterior a hoy.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
