package co.com.pragma.usecase.user;


import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.DuplicatedEmailException;
import co.com.pragma.model.user.exceptions.InvalidSalaryException;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;


public class CreateUserUseCaseTest {
	
	@InjectMocks
	private CreateUserUseCase createUserUseCase;
	
	@Mock
	private UserRepository userRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void mustSuccessfullyCreateUser() {
		User user = new User();
		user.setEmail("valid@email.com");
		user.setBaseSalary(new BigDecimal("2000000"));
		
		when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));
		when(userRepository.saveUser(user)).thenReturn(Mono.just(user));
		
		StepVerifier.create(createUserUseCase.execute(user))
			.expectNextMatches(saved ->
				saved.getEmail().equals("valid@email.com") &&
					saved.getBaseSalary().equals(new BigDecimal("2000000"))
			)
			.verifyComplete();
	}
	
	@Test
	void mustFailDuplicatedEmailCreateUser() {
		User user = User.builder()
			.firstName("John")
			.lastName("Doe")
			.birthDate(LocalDate.parse("1995-08-24"))
			.address("Fake Address")
			.phone("3112345678")
			.email("email@email.com")
			.baseSalary(new BigDecimal("1650000"))
			.build();
		
		when(userRepository.existsByEmail(user.getEmail()))
			.thenReturn(Mono.just(true));
		
		StepVerifier.create(createUserUseCase.execute(user))
			.expectErrorMatches(error -> error instanceof DuplicatedEmailException &&
				error.getMessage().contains("email@email.com"))
			.verify();
	}
	
	@Test
	void mustFailSalaryOverRangeCreateUser() {
		User user = User.builder()
			.firstName("John")
			.lastName("Doe")
			.birthDate(LocalDate.parse("1995-08-24"))
			.address("Fake Address")
			.phone("3112345678")
			.email("email@email.com")
			.baseSalary(new BigDecimal("16500000000"))
			.build();
		
		when(userRepository.existsByEmail(user.getEmail()))
			.thenReturn(Mono.just(false));
		
		StepVerifier.create(createUserUseCase.execute(user))
			.expectErrorMatches(error -> error instanceof InvalidSalaryException &&
				error.getMessage().contains("Salary must be between 0 and 15000000"))
			.verify();
	}
	
}
