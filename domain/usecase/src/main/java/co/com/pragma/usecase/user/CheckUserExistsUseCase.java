package co.com.pragma.usecase.user;

import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class CheckUserExistsUseCase {
	
	private final UserRepository userRepository;
	
	public Mono<Boolean> execute(String email, String identificationNumber) {
		return userRepository.existByEmailOrIdentification(email, identificationNumber);
	}
}
