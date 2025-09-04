package co.com.pragma.r2dbc.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.mapper.UserEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations
      <User, UserEntity, Long, UserReactiveRepository> implements UserRepository {

    private final TransactionalOperator transactionalOperator;
    private final UserEntityMapper userEntityMapper;

    public UserReactiveRepositoryAdapter(
		UserReactiveRepository repository,
		ObjectMapper mapper,
		TransactionalOperator transactionalOperator, UserEntityMapper userEntityMapper
	) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.transactionalOperator = transactionalOperator;
		this.userEntityMapper = userEntityMapper;
	}
    
    @Override
    public Mono<Boolean> existByEmailOrIdentification(String email, String identificationNumber) {
        return repository.existsByEmailOrIdentificationNumber(email, identificationNumber);
    }
    
    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
			.map(userEntityMapper::toDomain);
    }
    
    @Override
    public Mono<User> saveUser(User user) {
        return save(user)
              .as(transactionalOperator::transactional)
              .doOnSuccess(saved -> log.info("User saved {}", saved.getEmail()))
              .doOnError(e -> log.error("Error saving user"));
    }
}
