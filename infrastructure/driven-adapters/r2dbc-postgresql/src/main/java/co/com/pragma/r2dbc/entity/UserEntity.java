package co.com.pragma.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "usuario")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    
    @Id
    @Column("id_usuario")
    private Long id;

    @Column("nombres")
    private String firstName;

    @Column("apellidos")
    private String lastName;
    
    @Column("documento_identidad")
    private String identificationNumber;

    @Column("email")
    private String email;

    @Column("fecha_nacimiento")
    private LocalDate birthDate;

    @Column("telefono")
    private String phone;

    @Column("direccion")
    private String address;

    @Column("salario_base")
    private BigDecimal baseSalary;
    
    @Column("id_rol")
    private Short roleId;
    
}
