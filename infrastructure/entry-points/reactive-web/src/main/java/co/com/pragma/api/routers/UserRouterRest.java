package co.com.pragma.api.routers;

import co.com.pragma.api.constants.ApiConstants;
import co.com.pragma.api.dto.request.CreateUserRequest;
import co.com.pragma.api.handlers.UserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {
	
	@Bean
	@RouterOperations({
		@RouterOperation(
			path = ApiConstants.USER_PATH,
			beanMethod = "listenPOSTCreateUSer",
			method = RequestMethod.POST,
			operation = @Operation(
				tags = "usuario",
				operationId = "createUser",
				summary = "Crea un nuevo usuario",
				requestBody = @RequestBody(
					required = true,
					content = @Content(
						schema = @Schema(implementation = CreateUserRequest.class)
					)
				)
			)
		),
		@RouterOperation(
			path = ApiConstants.USER_EXISTS_PATH,
			beanMethod = "listenGETExistsUser",
			method = RequestMethod.GET,
			operation = @Operation(
				tags = "usuario",
				operationId = "existsUser",
				summary = "Verifica si un usuario existe por email e identificación"
			)
		)
	})
	public RouterFunction<ServerResponse> userRouterFunction(UserHandler handler) {
		return route(POST(ApiConstants.USER_PATH), handler::listenPOSTCreateUSer)
			.andRoute(GET(ApiConstants.USER_EXISTS_PATH), handler::listenGETExistsUser);
	}
}
