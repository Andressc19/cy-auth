package co.com.pragma.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    private static final String API_PATH = "/api/v1";

    @Bean
    @RouterOperations({
          @RouterOperation(
                path = API_PATH + "/usuarios",
                method = RequestMethod.GET,
                beanClass = UserHandler.class,
                beanMethod = "getAllUsers",
                operation = @Operation(
                      operationId = "getAllUsers",
                      summary = "Get all users",
                      responses = {
                            @ApiResponse(responseCode = "200", description = "OK")
                      }
                )
          ),
          @RouterOperation(
                path = API_PATH + "/usuarios",
                method = RequestMethod.POST,
                beanClass = UserHandler.class,
                beanMethod = "createUser",
                operation = @Operation(
                      operationId = "createUser",
                      summary = "Create a new user",
                      responses = {
                            @ApiResponse(responseCode = "201", description = "Created")
                      }
                )
          )
    })
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return route(GET(API_PATH + "/usuarios"), handler::getAllUsers)
              .andRoute(POST(API_PATH + "/usuarios"), handler::createUser);
    }
}
