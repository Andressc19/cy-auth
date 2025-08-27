package co.com.pragma.api;

import co.com.pragma.api.dto.request.CreateUserRequest;
import co.com.pragma.api.mappers.UserMapper;
import co.com.pragma.api.validators.RequestValidator;
import co.com.pragma.usecase.user.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;
    private final RequestValidator jakartaValidator;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(CreateUserRequest.class)
              .flatMap(jakartaValidator::validate)
              .map(userMapper::toDomain)
              .flatMap(createUserUseCase::execute)
              .map(userMapper::toDto)
              .doOnSuccess(dto -> log.info("Created user successfully: {}", dto))
              .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED).bodyValue(dto))
              .doOnError(e -> log.error("Error creating user"));
    }
}
