package co.com.pragma.model.user.validators;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.InvalidSalaryException;

import java.math.BigDecimal;

import static co.com.pragma.model.user.constants.UserConstants.MAX_SALARY;
import static co.com.pragma.model.user.constants.UserConstants.MIN_SALARY;


public class UserValidator {

    public static void validate(User user) {
        validateBaseSalary(user.getBaseSalary());
    }

    private static void validateBaseSalary(BigDecimal baseSalary) {
        if (baseSalary == null
              || baseSalary.compareTo(MIN_SALARY) < 0
              || baseSalary.compareTo(MAX_SALARY) > 0) {
            throw new InvalidSalaryException(baseSalary);
        }
    }
    
}