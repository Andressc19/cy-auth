package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);

    Flux<User> findAll();

    Mono<User> findById(String id);

    Mono<Void> delete(String id);

    Mono<User> update(User user);
}
