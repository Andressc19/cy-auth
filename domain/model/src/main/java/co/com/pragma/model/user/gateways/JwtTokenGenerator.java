package co.com.pragma.model.user.gateways;

import reactor.core.publisher.Mono;

public interface JwtTokenGenerator {
	Mono<String> generateAccessToken(String email, String roleName);
	Mono<String> getEmailFromToken(String token);
	Mono<Boolean> validateToken(String token);
}
