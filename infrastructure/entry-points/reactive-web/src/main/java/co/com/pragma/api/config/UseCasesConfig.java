package co.com.pragma.api.config;

import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

	private final UserRepository userRepository;

	@Bean
	public CreateUserUseCase createUserUseCase() {
		return new CreateUserUseCase(userRepository);
	}
}
