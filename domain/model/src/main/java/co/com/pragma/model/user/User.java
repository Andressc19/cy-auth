package co.com.pragma.model.user;

import co.com.pragma.model.userrole.UserRole;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private LocalDate birthDate;
    private String email;
    private String address;
    private String phone;
    private BigDecimal baseSalary;
    private UserRole role;
    
}