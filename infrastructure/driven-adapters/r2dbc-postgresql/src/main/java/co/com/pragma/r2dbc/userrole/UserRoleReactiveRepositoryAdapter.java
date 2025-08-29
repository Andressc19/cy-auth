package co.com.pragma.r2dbc.userrole;

import co.com.pragma.model.userrole.UserRole;
import co.com.pragma.model.userrole.gateways.UserRoleRepository;
import co.com.pragma.r2dbc.entity.UserRoleEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class UserRoleReactiveRepositoryAdapter extends ReactiveAdapterOperations
      <UserRole, UserRoleEntity, Short, UserRoleReactiveRepository> implements UserRoleRepository {


    public UserRoleReactiveRepositoryAdapter(UserRoleReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, UserRole.class));
    }
    
    @Override
    public Mono<UserRole> getUserRoleById(Short roleId) {
        return repository.findById(roleId)
            .map(entity -> mapper.map(entity, UserRole.class));
    }
}
