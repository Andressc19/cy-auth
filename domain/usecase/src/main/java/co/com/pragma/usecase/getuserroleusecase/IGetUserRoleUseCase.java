package co.com.pragma.usecase.getuserroleusecase;

import co.com.pragma.model.userrole.UserRole;
import reactor.core.publisher.Mono;


public interface IGetUserRoleUseCase {
	Mono<UserRole> getUserRoleById(Short roleId);
}
