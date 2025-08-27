package co.com.pragma.model.user.validators;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.InvalidSalaryException;

import java.math.BigDecimal;


public class UserValidator {

    private static final BigDecimal MIN_BASE_SALARY = BigDecimal.ZERO;
    private static final BigDecimal MAX_BASE_SALARY = new BigDecimal("15000000");

    public static void validate(User user) {
        validateBaseSalary(user.getBaseSalary());
    }

    private static void validateBaseSalary(BigDecimal baseSalary) {
        if (baseSalary == null
              || baseSalary.compareTo(MIN_BASE_SALARY) < 0
              || baseSalary.compareTo(MAX_BASE_SALARY) > 0) {
            throw new InvalidSalaryException(baseSalary);
        }
    }
}