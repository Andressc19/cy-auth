package co.com.pragma.model.user.validators;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.InvalidSalaryException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserValidatorTest {
    
    @Test
    void dummyTest() {
        System.out.println("JUnit is running!");
    }
    
    @Test
    void mustSuccessSalary () {
        User user = new User();
        user.setBaseSalary(new BigDecimal("100000"));
        assertDoesNotThrow(() -> UserValidator.validate(user));
    }

    @Test
    void mustFailNegativeSalary () {
        User user = new User();
        user.setBaseSalary(new BigDecimal("-1000000"));
        assertThrows(InvalidSalaryException.class, () -> UserValidator.validate(user));
    }
    
    @Test
    void mustFailOverSalary () {
        User user = new User();
        user.setBaseSalary(new BigDecimal("20000000"));
        assertThrows(InvalidSalaryException.class, () -> UserValidator.validate(user));
    }

    @Test
   void mustFailSalaryNull () {
        User user = new User();
        user.setBaseSalary(null);
        assertThrows(InvalidSalaryException.class, () -> UserValidator.validate(user));
    }
}
