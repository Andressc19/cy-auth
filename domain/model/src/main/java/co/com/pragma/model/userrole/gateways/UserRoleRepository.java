package co.com.pragma.model.userrole.gateways;

import co.com.pragma.model.userrole.UserRole;
import reactor.core.publisher.Mono;


public interface UserRoleRepository {
	Mono<UserRole> getUserRoleById(Short roleId);
}
