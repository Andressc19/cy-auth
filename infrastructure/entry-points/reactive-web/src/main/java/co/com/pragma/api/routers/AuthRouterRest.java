package co.com.pragma.api.routers;

import co.com.pragma.api.constants.ApiConstants;
import co.com.pragma.api.dto.request.AuthenticateUserRequest;
import co.com.pragma.api.handlers.AuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class AuthRouterRest {
	
	@Bean
	@RouterOperations({
		@RouterOperation(
			path = ApiConstants.AUTH_PATH,
			beanMethod = "listenPOSTAuthenticate",
			method = RequestMethod.POST,
			operation = @Operation(
				tags = "autenticacion",
				operationId = "authenticateUser",
				summary = "Autentica un usuario con email y contraseña",
				requestBody = @RequestBody(
					content = @Content(
						schema = @Schema(implementation = AuthenticateUserRequest.class)
					)
				)
			))}
	)
	public RouterFunction<ServerResponse> authRouterFunction(AuthHandler handler) {
		return route(POST(ApiConstants.AUTH_PATH), handler::listenPOSTAuthenticate);
	}
}
