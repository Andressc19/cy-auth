package co.com.pragma.usecase.getuserroleusecase;

import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.model.userrole.exceptions.RoleNotExistsException;
import co.com.pragma.model.userrole.gateways.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class GetUserRoleUseCase implements IGetUserRoleUseCase {
	
	private final UserRoleRepository userRoleRepository;
	
	@Override
	public Mono<UserRole> getUserRoleById(Short roleId) {
		return userRoleRepository.getUserRoleById(roleId)
			.switchIfEmpty(Mono.error(new RoleNotExistsException(roleId)));
	}
}
