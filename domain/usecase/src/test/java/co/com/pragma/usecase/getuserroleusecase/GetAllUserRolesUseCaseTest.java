package co.com.pragma.usecase.getuserroleusecase;

import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.model.userrole.gateways.UserRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GetAllUserRolesUseCaseTest {

	@InjectMocks
	private GetAllUserRolesUseCase getAllUserRolesUseCase;
	
	@Mock
	private UserRoleRepository userRoleRepository;

	
	private List<UserRole> defaultRoles (){
		return List.of(
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
	}
	
	
	@Test
	@DisplayName("Deberia obtener todos los roles satisfactoriamente")
	void mustGetAllUserRoles() {
		Flux<UserRole> fluxUserRoles = Flux.fromIterable(defaultRoles());
		
		when(userRoleRepository.getAllUserRoles())
			.thenReturn(fluxUserRoles);
		
		StepVerifier.create(userRoleRepository.getAllUserRoles())
			.expectNextMatches(role -> role.getId() == 1 && role.getName().equals("CLIENTE"))
			.expectNextMatches(role -> role.getId() == 2 && role.getName().equals("ADMINISTRADOR"))
			.expectNextMatches(role -> role.getId() == 3 && role.getName().equals("ASESOR"))
			.verifyComplete();
	}
	
	@Test
	@DisplayName("Deberia obtener una lista vacia")
	void mustGetAnEmptyListOfUserRoles() {
		when(userRoleRepository.getAllUserRoles())
			.thenReturn(Flux.empty());
		
		StepVerifier.create(userRoleRepository.getAllUserRoles())
			.expectNextCount(0)
			.verifyComplete();
	}
}
