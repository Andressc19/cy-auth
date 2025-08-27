package co.com.pragma.model.user;

import co.com.pragma.model.user.exceptions.InvalidSalaryException;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String address;
    private String phone;
    private BigDecimal baseSalary;
}