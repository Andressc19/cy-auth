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
public class CreateUserUseCase implements ICreateUserUserCase{

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    
    
    public Mono<User> createUser(User user) {
        return Mono.defer(()-> {
            user.setEmail(user.getEmail().toLowerCase());
            UserValidator.validate(user);
            
            return userRepository.existsByEmail(user.getEmail())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new DuplicatedEmailException(user.getEmail())))
                .then(userRoleRepository.getUserRoleById(user.getRole().getId()))
                .switchIfEmpty( Mono.error(new RoleNotExistsException(user.getRole().getId())))
                .flatMap(userRole -> {
                    user.setRole(userRole);
                   return userRepository.saveUser(user);
                });
        });
    }

}
