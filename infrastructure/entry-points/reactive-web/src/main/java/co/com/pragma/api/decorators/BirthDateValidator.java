package co.com.pragma.api.decorators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.isBlank() && !value.matches("\\d{4}-\\d{2}-\\d{2}") ) {
            return false;
        }
        LocalDate date = LocalDate.parse(value);
        return date.isBefore(LocalDate.now());
    }
}
