package co.com.pragma.api.handlers;

import co.com.pragma.api.dto.response.AuthenticateUserResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import co.com.pragma.api.dto.request.AuthenticateUserRequest;
import co.com.pragma.usecase.user.AuthenticateUserUseCase;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandler {

	private final AuthenticateUserUseCase authenticateUserUseCase;
	
	public Mono<ServerResponse> listenPOSTAuthenticate(ServerRequest request) {
		return request.bodyToMono(AuthenticateUserRequest.class)
			.doOnNext(req -> log.info("Attempting to validate user {}", req.email()))
			.flatMap(userDto -> authenticateUserUseCase.execute(userDto.email(), userDto.password()))
			.doOnNext( user -> log.info("User authenticated successfully"))
			.flatMap(token -> ServerResponse.ok().bodyValue(new AuthenticateUserResponse(token)));
	}
}
