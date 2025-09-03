package co.com.pragma.usecase.getuserroleusecase;

import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.model.userrole.gateways.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;


@RequiredArgsConstructor
public class GetAllUserRolesUseCase {
	
	private final UserRoleRepository userRoleRepository;
	
	public Flux<UserRole> execute() {
		return userRoleRepository.getAllUserRoles();
	}
}
