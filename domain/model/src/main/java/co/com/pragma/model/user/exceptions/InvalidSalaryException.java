package co.com.pragma.model.user.exceptions;

import java.math.BigDecimal;

import static co.com.pragma.model.user.constants.UserConstants.MAX_SALARY;
import static co.com.pragma.model.user.constants.UserConstants.MIN_SALARY;

public class InvalidSalaryException extends UserValidationException {
    public InvalidSalaryException(BigDecimal salary) {
        super("Salary must be between " + MIN_SALARY +" and " + MAX_SALARY +". Provided: " + salary);
    }
}
