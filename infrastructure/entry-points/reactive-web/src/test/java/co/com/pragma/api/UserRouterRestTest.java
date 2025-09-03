package co.com.pragma.api;

import co.com.pragma.api.constants.ApiConstants;
import co.com.pragma.api.dto.request.CreateUserRequest;
import co.com.pragma.api.dto.request.UserExistsRequest;
import co.com.pragma.api.dto.response.CreateUserResponse;
import co.com.pragma.api.dto.response.UserExistsResponse;
import co.com.pragma.api.handlers.UserHandler;
import co.com.pragma.api.mappers.UserMapper;
import co.com.pragma.api.exceptions.RequestValidator;
import co.com.pragma.api.routers.UserRouterRest;
import co.com.pragma.model.user.User;
import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.usecase.user.CheckUserExistsUseCase;
import co.com.pragma.usecase.user.CreateUserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

@ContextConfiguration(classes = {
	UserRouterRest.class,
	UserHandler.class,
	UserRouterRestTest.TestConfig.class }
)
@WebFluxTest
class UserRouterRestTest {
	
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockitoBean
	private CreateUserUseCase createUserUseCase;
	
	@MockitoBean
	private CheckUserExistsUseCase checkUserExistsUseCase;
	
	@MockitoBean
	private UserMapper userMapper;
	
	@MockitoBean
	private RequestValidator jakartaValidator;
	
	
	@TestConfiguration
	static class TestConfig {
		@Bean
		public CreateUserUseCase createUserUseCase() {
			return Mockito.mock(CreateUserUseCase.class);
		}
		
		@Bean
		public UserMapper userMapper() {
			return Mockito.mock(UserMapper.class);
		}
		
		@Bean
		public RequestValidator jakartaValidator() {
			return Mockito.mock(RequestValidator.class);
		}
		
		@Bean
		public CheckUserExistsUseCase checkUserExistsUseCase() {
			return Mockito.mock(CheckUserExistsUseCase.class);
		}
	}
	
	
	private UserRole defaultUserRole() {
		return UserRole.builder()
			.id(Short.parseShort("1"))
			.name("CLIENTE")
			.description("Descripcion cliente")
			.build();
		
	}
	
	private User defaultUser() {
		return User.builder()
			.firstName("John")
			.lastName("Doe")
			.birthDate(LocalDate.parse("1995-08-24"))
			.address("Cra 123 #45-67")
			.phone("3001234567")
			.email("johndoe@mail.com")
			.baseSalary(new BigDecimal("2500000"))
			.role(defaultUserRole())
			.build();
	}
	
	
	@Test
	@DisplayName("Deberia crear un usuario satisfactoriamente")
	void mustCreateUserAndReturnCreatedUser() {
		
		User user = defaultUser();
		UserRole userRole = defaultUserRole();
		
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
		
		when(createUserUseCase.execute(any(User.class)))
			.thenReturn(Mono.just(user));
		
		when(userMapper.toDto(any(User.class)))
			.thenReturn(responseDto);
		
		webTestClient.post()
			.uri(ApiConstants.USER_PATH)
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
	
	@Test
	void mustExistsAnUserByEmailAndIdentificationNumber() {

		String email = "johndoe@mail.com";
		String identificationNumber = "1234567";
		
		UserExistsRequest request = new UserExistsRequest(email, identificationNumber);
		
		when(checkUserExistsUseCase.execute(any(String.class), any(String.class)))
			.thenReturn(Mono.just(true));
		
		webTestClient.post()
			.uri(ApiConstants.USER_EXISTS_PATH)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(request)
			.exchange()
			.expectStatus().isOk()
			.expectBody(UserExistsResponse.class)
			.value(response -> {
				Assertions.assertThat(response.exists()).isEqualTo(true);
			});
	}
	
	@Test
	void mustFailedExistsAnUserByEmailAndIdentificationNumber() {
		
		String email = "johndoe@mail.com";
		String identificationNumber = "1234567";
		
		UserExistsRequest request = new UserExistsRequest(email, identificationNumber);
		
		when(checkUserExistsUseCase.execute(any(String.class), any(String.class)))
			.thenReturn(Mono.just(false));
		
		webTestClient.post()
			.uri(ApiConstants.USER_EXISTS_PATH)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(request)
			.exchange()
			.expectStatus().isOk()
			.expectBody(UserExistsResponse.class)
			.value(response -> {
				Assertions.assertThat(response.exists()).isEqualTo(false);
			});
	}
	
}
