package co.com.pragma.usecase.user;

import co.com.pragma.model.user.gateways.JwtTokenGenerator;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class CheckUserExistsUseCase {
	
	private final UserRepository userRepository;
	private final JwtTokenGenerator jwtTokenGenerator;
	
	public Mono<Boolean> execute(String jwtToken) {
		return jwtTokenGenerator.getEmailFromToken(jwtToken)
			.flatMap(userRepository::existByEmail);
	}
}
