package co.com.pragma.api.handlers;

import co.com.pragma.api.dto.request.CreateUserRequest;
import co.com.pragma.api.dto.response.UserExistsResponse;
import co.com.pragma.api.mappers.UserMapper;
import co.com.pragma.api.exceptions.RequestValidator;
import co.com.pragma.usecase.user.CheckUserExistsUseCase;
import co.com.pragma.usecase.user.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
	private final CheckUserExistsUseCase checkUserExistsUseCase;
	private final UserMapper userMapper;
	private final RequestValidator jakartaValidator;
	
	public Mono<ServerResponse> listenPOSTCreateUSer(ServerRequest request) {
		return request.bodyToMono(CreateUserRequest.class)
			.doOnNext(req -> log.info("Attempting to create user: {}", req.email()))
			.flatMap(jakartaValidator::validate)
			.map(userMapper::toDomain)
			.flatMap(createUserUseCase::execute)
			.flatMap(user -> {
				log.info("Checking role: {}", user.getEmail());
				return Mono.just(user);
			} )
			.map(userMapper::toDto)
			.doOnNext(user -> log.info("Created user successfully: {}", user.email()))
			.flatMap(dto -> ServerResponse.status(HttpStatus.CREATED).bodyValue(dto));
	}
	
	public Mono<ServerResponse> listenGETExistsUser(ServerRequest request) {
		log.info("Attempting to check user exists by authToken");
		String authToken = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);
		
		return checkUserExistsUseCase.execute(authToken)
			.flatMap(exist -> ServerResponse.ok()
				.bodyValue(new UserExistsResponse(exist)));
	}

}
