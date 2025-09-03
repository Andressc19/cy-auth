package co.com.pragma.usecase.user;

import co.com.pragma.model.user.exceptions.UserNotFoundException;
import co.com.pragma.model.user.gateways.PasswordEncryptor;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class AuthenticateUserUseCase {
	
	private final UserRepository userRepository;
	private final PasswordEncryptor passwordEncryptor;
	
	public Mono<Boolean> execute(String email, String password) {
		return userRepository.findByEmail(email)
			.switchIfEmpty(Mono.error(new UserNotFoundException(email)))
			.flatMap( user ->
				passwordEncryptor.matches(password, user.getPassword())
			);
	}
}
