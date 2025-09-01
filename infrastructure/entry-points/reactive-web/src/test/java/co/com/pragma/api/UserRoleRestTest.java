package co.com.pragma.api;

import co.com.pragma.api.constants.ApiConstants;
import co.com.pragma.api.dto.response.GetUserRoleResponse;
import co.com.pragma.api.exceptions.RequestValidator;
import co.com.pragma.api.handlers.UserRoleHandler;
import co.com.pragma.api.mappers.UserRoleMapper;
import co.com.pragma.api.routers.UserRoleRouterRest;
import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.usecase.getuserroleusecase.IGetAllUserRolesUseCase;
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
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ContextConfiguration(classes = {
	UserRoleRouterRest.class,
	UserRoleHandler.class,
	UserRoleRestTest.TestConfig.class
})
@WebFluxTest
public class UserRoleRestTest {
	
	@Autowired
	private WebTestClient webClient;
	
	@MockitoBean
	private IGetAllUserRolesUseCase getAllUserRolesUseCase;
	
	@MockitoBean
	private UserRoleMapper userRoleMapper;
	
	@MockitoBean
	private RequestValidator jakartaValidator;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@TestConfiguration
	static class TestConfig {
		@Bean
		public IGetAllUserRolesUseCase getAllUserRolesUseCase() {
			return Mockito.mock(IGetAllUserRolesUseCase.class);
		}
		
		@Bean
		public UserRoleMapper getUserRoleMapper() {
			return Mockito.mock(UserRoleMapper.class);
		}
		
		@Bean
		public RequestValidator getRequestValidator() {
			return Mockito.mock(RequestValidator.class);
		}
	}
	
	@Test
	@DisplayName("Deberia retornar varios roles de usuario")
	void mustReturnAllUserRoles(){
		
		Flux<UserRole> fluxUserRoles = Flux.just(
			new UserRole().toBuilder()
				.id((short) 1).name("CLIENTE").description("Descripcion cliente")
				.build(),
			new UserRole().toBuilder()
				.id((short) 2).name("ADMINISTRADOR").description("Descripcion cliente")
				.build(),
			new UserRole().toBuilder()
				.id((short) 3).name("ASESOR").description("Descripcion cliente")
				.build()
		);
		
		when(getAllUserRolesUseCase.execute())
			.thenReturn(fluxUserRoles);
		
		when(userRoleMapper.toDto(any(UserRole.class)))
			.thenAnswer(invocation -> {
				UserRole role = invocation.getArgument(0);
				return new GetUserRoleResponse(role.getId(), role.getName(), role.getDescription());
			});
		
		webTestClient.get()
			.uri(ApiConstants.USER_ROLES_PATH)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(GetUserRoleResponse.class)
			.value(responses -> {
				assertNotNull(responses);
				assertEquals(3, responses.size());
				assertEquals("CLIENTE", responses.get(0).name());
				assertEquals("ADMINISTRADOR", responses.get(1).name());
				assertEquals("ASESOR", responses.get(2).name());
			});
	}
	
	@Test
	@DisplayName("Deberia retornar una lista vacia de roles")
	void mustReturnVoidListUserRoles() {
		when(getAllUserRolesUseCase.execute())
			.thenReturn(Flux.empty());
		
		webTestClient.get()
			.uri(ApiConstants.USER_ROLES_PATH)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(GetUserRoleResponse.class)
			.value(responses -> {
				assertNotNull(responses);
				assertEquals(0, responses.size());
			});
	}
	
}
