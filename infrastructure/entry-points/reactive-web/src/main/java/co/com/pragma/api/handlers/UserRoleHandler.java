package co.com.pragma.api.handlers;

import co.com.pragma.api.mappers.UserRoleMapper;
import co.com.pragma.usecase.getuserroleusecase.GetAllUserRolesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class UserRoleHandler {
	
	private final GetAllUserRolesUseCase getAllUserRolesUseCase;
	private final UserRoleMapper userRoleMapper;
	
	public Mono<ServerResponse> listenGETAllUserRoles(ServerRequest request){
		return getAllUserRolesUseCase.execute()
			.map(userRoleMapper::toDto)
			.collectList()
			.flatMap( list -> ServerResponse.ok().bodyValue(list));
	}
	
}