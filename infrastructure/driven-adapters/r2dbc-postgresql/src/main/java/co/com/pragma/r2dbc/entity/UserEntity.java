package co.com.pragma.r2dbc.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

import java.time.LocalDate;


@Table(name = "users")
@Builder
public class UserEntity {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String address;
    private String phone;
}
