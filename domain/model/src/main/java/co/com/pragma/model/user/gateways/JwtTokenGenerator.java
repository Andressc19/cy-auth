package co.com.pragma.model.user.gateways;

import reactor.core.publisher.Mono;

import java.util.List;

public interface JwtTokenGenerator {
	Mono<String> generateAccessToken(String email, List<String> roles);
	Mono<String> getEmailFromToken(String token);
	Mono<Boolean> validateToken(String token);
	Mono<List<String>> getRolesFromToken(String token);
}
