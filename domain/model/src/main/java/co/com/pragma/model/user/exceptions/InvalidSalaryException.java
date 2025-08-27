package co.com.pragma.model.user.exceptions;

import java.math.BigDecimal;

public class InvalidSalaryException extends UserValidationException {
    public InvalidSalaryException(BigDecimal salary) {
        super("Salary must be between 0 and 15,000,000. Provided: " + salary);
    }
}
