package co.com.pragma.api.decorators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentificationValidator implements ConstraintValidator<ValidIdentificationNumber, String> {
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
			return false;
		}
		return value.matches("^[0-9]{1,15}$");
	}
}
