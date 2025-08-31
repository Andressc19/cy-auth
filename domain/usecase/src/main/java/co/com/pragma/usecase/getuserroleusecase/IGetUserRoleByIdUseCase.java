package co.com.pragma.usecase.getuserroleusecase;

import co.com.pragma.model.userrole.UserRole;
import reactor.core.publisher.Mono;

public interface IGetUserRoleByIdUseCase {
	Mono<UserRole> execute(Short id);
}
