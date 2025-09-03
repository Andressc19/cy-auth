package co.com.pragma.r2dbc.userrole;

import co.com.pragma.r2dbc.entity.UserRoleEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRoleReactiveRepository extends ReactiveCrudRepository
    <UserRoleEntity, Short>, ReactiveQueryByExampleExecutor<UserRoleEntity> {}
