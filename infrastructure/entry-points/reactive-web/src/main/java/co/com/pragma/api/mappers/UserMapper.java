package co.com.pragma.api.mappers;

import co.com.pragma.api.dto.request.CreateUserRequest;
import co.com.pragma.api.dto.response.CreateUserResponse;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(CreateUserRequest dto);
    CreateUserResponse toDto(User entity);
}
