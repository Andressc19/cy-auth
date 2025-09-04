package co.com.pragma.api.enums;

import lombok.Getter;

@Getter
public enum RoleType {
	CUSTOMER("CLIENTE"),
	ADVISOR("ASESOR"),
	ADMIN("ADMINISTRADOR");
	
	private final String name;
	
	RoleType( String name ){
		this.name = name;
	}
	
}
