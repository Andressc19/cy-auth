package co.com.pragma.api.mappers;

import co.com.pragma.api.dto.request.CreateUserRequest;
import co.com.pragma.api.dto.response.CreateUserResponse;
import co.com.pragma.model.user.User;
import co.com.pragma.model.userrole.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    User toDomain(CreateUserRequest dto);
    
    @Mapping(target = "roleId", source = "role.id")
    CreateUserResponse toDto(User domain);
    
    @Named("mapRole")
    default UserRole mapRole(Short roleId) {
        if (roleId == null) return null;
        return UserRole.builder().id(roleId).build();
    }
}
