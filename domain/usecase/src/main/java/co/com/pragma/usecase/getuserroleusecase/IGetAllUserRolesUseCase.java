package co.com.pragma.usecase.getuserroleusecase;

import co.com.pragma.model.userrole.UserRole;
import reactor.core.publisher.Flux;


public interface IGetAllUserRolesUseCase {
	Flux<UserRole> execute();
}
