package co.com.pragma.api.mappers;

import co.com.pragma.api.dto.response.GetUserRoleResponse;
import co.com.pragma.model.userrole.UserRole;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserRoleMapper {
	GetUserRoleResponse toDto(UserRole userRoles);
}
