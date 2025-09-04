package co.com.pragma.passwordencoder;

import co.com.pragma.model.user.gateways.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class BCryptPasswordEncryptor implements PasswordEncryptor {
	
	private final PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public Mono<String> encrypt(String password) {
		return Mono.fromCallable(() -> encoder.encode(password))
			.subscribeOn(Schedulers.boundedElastic());
	}
	
	@Override
	public Mono<Boolean> matches(String password, String encryptedPassword) {
		return Mono.fromCallable(() -> encoder.matches(password, encryptedPassword))
			.subscribeOn(Schedulers.boundedElastic());
	}
	
}
