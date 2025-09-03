package co.com.pragma.r2dbc.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("rol_usuario")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleEntity {
	
	@Id
	@Column("id_rol")
	private Short id;
	
	@Column("nombre")
	private String name;
	
	@Column("descripcion")
	private String description;
}
