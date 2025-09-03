package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.mapper.UserEntityMapper;
import co.com.pragma.r2dbc.user.UserReactiveRepository;
import co.com.pragma.r2dbc.user.UserReactiveRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {
	
	@Mock
	private UserReactiveRepository repository;
	
	@Mock
	private ObjectMapper mapper;
	
	@Mock
	private TransactionalOperator transactionalOperator;
	
	@Mock
	private UserEntityMapper userEntityMapper;
	
	private UserReactiveRepositoryAdapter repositoryAdapter;
	
	
	@BeforeEach
	void setUp() {
		repositoryAdapter = new UserReactiveRepositoryAdapter(
			repository,
			mapper,
			transactionalOperator,
			userEntityMapper
		);
	}
	
	
	@Test
	void mustSaveValue() {
		User user = User.builder()
			.firstName("John")
			.lastName("Doe")
			.birthDate(LocalDate.parse("1995-08-24"))
			.address("Fake Address")
			.phone("3112345678")
			.email("email@email.com")
			.baseSalary(new BigDecimal("1650000"))
			.build();
		
		UserEntity userEntity = UserEntity.builder()
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.birthDate(user.getBirthDate())
			.address(user.getAddress())
			.phone(user.getPhone())
			.email(user.getEmail())
			.baseSalary(user.getBaseSalary())
			.build();
		
		when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
		when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
		when(userEntityMapper.toDomain(userEntity)).thenReturn(user);
		when(transactionalOperator.transactional(ArgumentMatchers.<Mono<User>>any()))
			.thenAnswer(invocation -> invocation.getArgument(0));
		
		Mono<User> result = repositoryAdapter.saveUser(user);
		
		StepVerifier.create(result)
			.expectNextMatches(saved ->
				saved.getEmail().equals(user.getEmail()) &&
					saved.getFirstName().equals(user.getFirstName()) &&
					saved.getBaseSalary().equals(user.getBaseSalary())
			)
			.verifyComplete();
	}
}
