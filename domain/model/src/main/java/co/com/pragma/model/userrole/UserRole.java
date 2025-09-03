package co.com.pragma.model.userrole;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class UserRole {
	
	private Short id;
	private String name;
	private String description;

}
