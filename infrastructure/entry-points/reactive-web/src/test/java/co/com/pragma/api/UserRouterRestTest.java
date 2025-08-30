package co.com.pragma.api;

import co.com.pragma.api.dto.request.CreateUserRequest;
import co.com.pragma.api.dto.response.CreateUserResponse;
import co.com.pragma.api.mappers.UserMapper;
import co.com.pragma.api.exceptions.RequestValidator;
import co.com.pragma.model.user.User;
import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.usecase.getuserroleusecase.GetUserRoleUseCase;
import co.com.pragma.usecase.user.CreateUserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserRouterRest.class, UserHandler.class, UserRouterRestTest.TestConfig.class})
@WebFluxTest
class UserRouterRestTest {
	
	private final String API_URL = "/api/v1";
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockitoBean
	private CreateUserUseCase createUserUseCase;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RequestValidator jakartaValidator;
	@Autowired
	private GetUserRoleUseCase getUserRoleUseCase;
	
	@TestConfiguration
	static class TestConfig {
		@Bean
		public CreateUserUseCase createUserUseCase() {
			return Mockito.mock(CreateUserUseCase.class);
		}
		
		@Bean
		public GetUserRoleUseCase getUserRoleUseCase() {
			return Mockito.mock(GetUserRoleUseCase.class);
		}
		
		@Bean
		public UserMapper userMapper() {
			return Mockito.mock(UserMapper.class);
		}
		
		@Bean
		public RequestValidator jakartaValidator() {
			return Mockito.mock(RequestValidator.class);
		}
	}
	
	@Test
	void createUserShouldReturnCreatedUser() {
		
		UserRole userRole = UserRole.builder()
			.id(Short.parseShort("1"))
			.name("CLIENTE")
			.description("Descripcion cliente")
			.build();
		
		User user = User.builder()
			.firstName("John")
			.lastName("Doe")
			.birthDate(LocalDate.parse("1995-08-24"))
			.address("Cra 123 #45-67")
			.phone("3001234567")
			.email("johndoe@mail.com")
			.baseSalary(new BigDecimal("2500000"))
			.role(userRole)
			.build();
		
		CreateUserRequest request = new CreateUserRequest(
			user.getFirstName(),
			user.getLastName(),
			user.getIdentificationNumber(),
			user.getBirthDate().toString(),
			user.getAddress(),
			user.getPhone(),
			user.getEmail(),
			user.getBaseSalary(),
			user.getRole().getId()
		);
		
		CreateUserResponse responseDto = new CreateUserResponse(
			user.getFirstName(),
			user.getLastName(),
			user.getBirthDate(),
			user.getIdentificationNumber(),
			user.getAddress(),
			user.getPhone(),
			user.getEmail(),
			user.getBaseSalary(),
			userRole.getId()
		);
		
		when(jakartaValidator.validate(any(CreateUserRequest.class)))
			.thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
		
		when(userMapper.toDomain(request))
			.thenReturn(user);
		
		when(getUserRoleUseCase.getUserRoleById(any(Short.class)))
			.thenReturn(Mono.just(userRole));
		
		when(createUserUseCase.createUser(any(User.class)))
			.thenReturn(Mono.just(user));
		
		when(userMapper.toDto(user))
			.thenReturn(responseDto);
		
		webTestClient.post()
			.uri(API_URL + "/usuarios")
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(request)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(CreateUserResponse.class)
			.value(response -> {
				Assertions.assertThat(response.email()).isEqualTo("johndoe@mail.com");
				Assertions.assertThat(response.firstName()).isEqualTo("John");
				}
			);
	}
	
}
