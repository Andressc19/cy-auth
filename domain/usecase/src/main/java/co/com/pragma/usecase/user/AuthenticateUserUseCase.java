package co.com.pragma.usecase.user;

import co.com.pragma.model.user.exceptions.InvalidCredentialsException;
import co.com.pragma.model.user.exceptions.UserNotFoundException;
import co.com.pragma.model.user.gateways.JwtTokenGenerator;
import co.com.pragma.model.user.gateways.PasswordEncryptor;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.userrole.exceptions.RoleNotExistsException;
import co.com.pragma.model.userrole.gateways.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;


@RequiredArgsConstructor
public class AuthenticateUserUseCase {
	
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncryptor passwordEncryptor;
	private final JwtTokenGenerator jwtTokenGenerator;
	
	public Mono<String> execute(String email, String password) {
		return userRepository.findByEmail(email)
			.switchIfEmpty(Mono.error(new UserNotFoundException(email)))
			.flatMap(user -> passwordEncryptor.matches(password, user.getPassword())
				.filter(isAuth -> isAuth)
				.switchIfEmpty(Mono.error(new InvalidCredentialsException()))
				.thenReturn(user)
			)
			.flatMap(user -> userRoleRepository.findById(user.getRole().getId())
				.switchIfEmpty(Mono.error(new RoleNotExistsException(user.getRole().getId())))
				.flatMap(userRole -> jwtTokenGenerator.generateAccessToken(user.getEmail(), List.of(userRole.getName())))
			);
	}
}
