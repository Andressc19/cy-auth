package co.com.pragma.api;

import co.com.pragma.api.dto.request.CreateUserRequest;
import co.com.pragma.api.dto.response.CreateUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {

    private static final String API_PATH = "/api/v1";

    @Bean
    @RouterOperations({
          @RouterOperation(
                path = API_PATH + "/usuarios",
                beanClass = UserHandler.class,
                beanMethod = "createUser",
                method = RequestMethod.POST,
                operation = @Operation(
                      operationId = "createUser",
                      summary = "Crea un nuevo usuario",
                      requestBody = @RequestBody(
                            required = true,
                            content = @Content(
                                  schema = @Schema(implementation = CreateUserRequest.class)
                            )
                      ),
                      responses = {
                            @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(schema = @Schema(implementation = CreateUserResponse.class))),
                            @ApiResponse(responseCode = "400", description = "Error de validación"),
                            @ApiResponse(responseCode = "500", description = "Error interno")
                      }
                )
          )
    })
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return route(POST(API_PATH + "/usuarios"), handler::listenPOSTCreateUSer)
              .filter((request, next) -> next.handle(request));
    }
}
