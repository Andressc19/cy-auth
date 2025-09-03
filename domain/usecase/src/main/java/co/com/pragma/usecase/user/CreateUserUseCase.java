package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.DuplicatedEmailException;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.user.validators.UserValidator;
import co.com.pragma.model.userrole.exceptions.RoleNotExistsException;
import co.com.pragma.model.userrole.gateways.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class CreateUserUseCase {
    
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    
    public Mono<User> execute(User user) {
        return Mono.defer(()-> {
            user.setEmail(user.getEmail().toLowerCase());
            UserValidator.validate(user);
            
            return userRepository.existsUser(user.getEmail(), user.getIdentificationNumber())
                .filter(userExists -> !userExists)
                .switchIfEmpty(Mono.error(new DuplicatedEmailException(user.getEmail())))
                .flatMap( exists -> userRoleRepository.existsById(user.getRole().getId()))
                    .filter(exist -> exist)
                    .switchIfEmpty(Mono.error(new RoleNotExistsException(user.getRole().getId())))
                .flatMap( roleExists -> userRepository.saveUser(user));
        });
    }
}
