package co.com.pragma.api.decorators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentificationValidator implements ConstraintValidator<ValidIdentificationNumber, String> {
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (!value.isEmpty()){
			return false;
		}
		return value.matches("^[0-9]{10}$");
	}
}
