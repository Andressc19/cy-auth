package co.com.pragma.usecase.user;


import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.DuplicatedUserException;
import co.com.pragma.model.user.exceptions.InvalidSalaryException;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.model.userrole.exceptions.RoleNotExistsException;
import co.com.pragma.model.userrole.gateways.UserRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {
	
	@InjectMocks
	private CreateUserUseCase createUserUseCase;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserRoleRepository userRoleRepository;
	
	
	// To create a default role for test
	private UserRole defaultUserRole() {
		return UserRole.builder().id((short) 1).build();
	}
	
	// To create a default user for test
	private User defaultUser() {
		return User.builder()
			.firstName("John")
			.lastName("Doe")
			.birthDate(LocalDate.parse("1995-08-24"))
			.address("Fake Address")
			.phone("3112345678")
			.email("email@email.com")
			.baseSalary(new BigDecimal("2000000"))
			.role(defaultUserRole())
			.build();
	}
	
	@Test
	@DisplayName("Debe crear un usuario")
	void mustSuccessfullyCreateUser() {
		User user = defaultUser();
		
		when(userRepository.existsByEmailOrIdentification(user.getEmail(), user.getIdentificationNumber()))
			.thenReturn(Mono.just(false));
		
		when(userRoleRepository.existsById(user.getRole().getId()))
			.thenReturn(Mono.just(true));
		
		when(userRepository.saveUser(user))
			.thenReturn(Mono.just(user));
		
		StepVerifier.create(createUserUseCase.execute(user))
			.expectNextMatches(saved ->
				saved.getEmail().equals("email@email.com") &&
					saved.getBaseSalary().equals(new BigDecimal("2000000"))
			)
			.verifyComplete();
	}
	
	@Test
	@DisplayName("Debe fallar por email de usuario duplicado")
	void mustFailDuplicatedEmailCreateUser() {
		User user = defaultUser();
		
		when(userRepository.existsByEmailOrIdentification(user.getEmail(), user.getIdentificationNumber()))
			.thenReturn(Mono.just(true));

		StepVerifier.create(createUserUseCase.execute(user))
			.expectErrorMatches(error -> error instanceof DuplicatedUserException &&
				error.getMessage().contains("email@email.com"))
			.verify();
	}
	
	@Test
	@DisplayName("Debe fallar por role no existente")
	void mustFailRoleDoesntExist() {
		User user = defaultUser();
		
		when(userRepository.existsByEmailOrIdentification(user.getEmail(), user.getIdentificationNumber()))
			.thenReturn(Mono.just(false));
		
		when(userRoleRepository.existsById(user.getRole().getId()))
			.thenReturn(Mono.just(false));
		
		StepVerifier.create(createUserUseCase.execute(user))
			.expectErrorMatches(error -> error instanceof RoleNotExistsException &&
				error.getMessage().contains("Role")
			).verify();
	}
	
	@Test
	@DisplayName("Debe fallar por el salario superior al maximo permitido")
	void mustFailSalaryOverRangeCreateUser() {
		User user = defaultUser();
		user.setBaseSalary(new BigDecimal("2000000000"));
		
		StepVerifier.create(createUserUseCase.execute(user))
			.expectErrorMatches(error -> error instanceof InvalidSalaryException)
			.verify();
	}
	
	@Test
	@DisplayName("Debe fallar por el salario inferior al minimo permitido")
	void mustFailSalaryInferiorRangeCreateUser() {
		User user = defaultUser();
		user.setBaseSalary(new BigDecimal("-100000"));
		
		StepVerifier.create(createUserUseCase.execute(user))
			.expectErrorMatches(error -> error instanceof InvalidSalaryException)
			.verify();
	}
	
}
