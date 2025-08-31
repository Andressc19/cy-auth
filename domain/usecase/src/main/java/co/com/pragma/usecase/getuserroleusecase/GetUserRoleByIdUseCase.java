package co.com.pragma.usecase.getuserroleusecase;

import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.model.userrole.gateways.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class GetUserRoleByIdUseCase implements IGetUserRoleByIdUseCase{
	
	private final UserRoleRepository userRoleRepository;
	
	@Override
	public Mono<UserRole> execute(Short id) {
		return userRoleRepository.getUserRoleById(id);
	}
}
