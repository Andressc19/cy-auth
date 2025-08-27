package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.DuplicatedEmailException;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.user.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public Mono<User> execute(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        UserValidator.validate(user);
        return userRepository.existsByEmail(user.getEmail()).flatMap(exists -> {
            if (exists) return Mono.error(new DuplicatedEmailException(user.getEmail()));
            return userRepository.saveUser(user);
        });
    }
}
