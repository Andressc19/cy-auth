package co.com.pragma.model.user.gateways;

import reactor.core.publisher.Mono;

public interface PasswordEncryptor {
	Mono<String> encrypt(String password);
	Mono<Boolean> matches(String password, String encryptedPassword);
}
