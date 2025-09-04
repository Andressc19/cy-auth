package co.com.pragma.api.routers;

import co.com.pragma.api.constants.ApiConstants;
import co.com.pragma.api.dto.response.GetUserRoleResponse;
import co.com.pragma.api.handlers.UserRoleHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRoleRouterRest {
	
	
	@Bean
	@RouterOperations({
		@RouterOperation(
			path = ApiConstants.USER_ROLES_PATH,
			beanClass = UserRoleHandler.class,
			beanMethod = "listenGETAllUserRoles",
			method = RequestMethod.GET,
			operation = @Operation(
				tags = "rol-usuario",
				operationId = "getAllUserRoles",
				summary = "Obtiene todos los roles",
				responses = {
					@ApiResponse(responseCode = "200", description = "Usuario creado",
						content = @Content(schema = @Schema(implementation = GetUserRoleResponse.class))),
				
				}
			)
		)
	})
	public RouterFunction<ServerResponse> userRoleRouterFunction(UserRoleHandler handler) {
		return route(GET( ApiConstants.USER_ROLES_PATH ), handler::listenGETAllUserRoles);
	}
}
