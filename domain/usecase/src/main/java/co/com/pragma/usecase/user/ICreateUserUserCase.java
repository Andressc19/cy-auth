package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

public interface ICreateUserUserCase {
	Mono<User> execute(User user);
}
