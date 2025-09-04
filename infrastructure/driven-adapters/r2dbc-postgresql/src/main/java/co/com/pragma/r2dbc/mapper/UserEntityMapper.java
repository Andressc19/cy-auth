package co.com.pragma.r2dbc.mapper;


import co.com.pragma.model.user.User;
import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.r2dbc.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
	@Mapping(target="roleId", source = "role.id")
	UserEntity toEntity(User user);
	
	@Mapping(target = "role", source = "roleId", qualifiedByName="mapRole" )
	User toDomain(UserEntity entity);
	
	@Named("mapRole")
	default UserRole mapRole(Short roleId) {
		if (roleId == null) return null;
		return new UserRole()
			.toBuilder().id(roleId).build();
	}
}
