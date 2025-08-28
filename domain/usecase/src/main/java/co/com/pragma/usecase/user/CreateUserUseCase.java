package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.DuplicatedEmailException;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.user.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class CreateUserUseCase implements ICreateUserUserCase{

    private final UserRepository userRepository;

    public Mono<User> execute(User user) {
        return Mono.defer(()-> {
            user.setEmail(user.getEmail().toLowerCase());
            UserValidator.validate(user);
            
            return userRepository.existsByEmail(user.getEmail())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new DuplicatedEmailException(user.getEmail())))
                .then(userRepository.saveUser(user));
        });
    }

}
