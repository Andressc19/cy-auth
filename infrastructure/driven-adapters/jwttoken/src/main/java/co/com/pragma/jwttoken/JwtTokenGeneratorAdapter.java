package co.com.pragma.jwttoken;

import co.com.pragma.model.user.gateways.JwtTokenGenerator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenGeneratorAdapter implements JwtTokenGenerator {
	
	private final JwtProperties properties;
	private final SecretKey key;
	
	public JwtTokenGeneratorAdapter(JwtProperties properties) {
		this.properties = properties;
		this.key = Keys.hmacShaKeyFor(properties.secret().getBytes(StandardCharsets.UTF_8));
	}
	
	@Override
	public Mono<String> generateAccessToken(String email, List<String> roles) {
		return Mono.fromCallable(() -> {
			
			Instant now = Instant.now();
			Instant expiry = now.plusSeconds(Long.parseLong(properties.expires()));
			
			return Jwts.builder()
				.subject(email)
				.claim("roles", roles)
				.issuedAt(new Date())
				.expiration(Date.from(expiry))
				.signWith(key)
				.compact();
		});
	}
	
	// Get Email from token
	@Override
	public Mono<String> getEmailFromToken(String token) {
		return Mono.fromCallable(() -> parseToken(token).getSubject());
	}
	
	@Override
	public Mono<List<String>> getRolesFromToken(String token) {
		return Mono.fromCallable(() -> {
			Object rolesClaim = parseToken(token).get("roles");
			if (rolesClaim instanceof String role) {
				return List.of(role);
			} else if (rolesClaim instanceof List<?> list) {
				return list.stream()
					.map(Object::toString)
					.toList();
			} else {
				return List.of();
			}
		});
	}
	
	// To get Claims
	private Claims parseToken(String token) {
		if(token.startsWith("Bearer")) {
			token = token.substring(7);
		}
		return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
	
	@Override
	public Mono<Boolean> validateToken(String token) {
		return Mono.fromCallable(() -> {
				try {
					Jws<Claims> claimsJwt = Jwts.parser()
						.verifyWith(key)
						.build()
						.parseSignedClaims(token);
					
					Date expiration = claimsJwt.getPayload().getExpiration();
					return !expiration.before(new Date());
				} catch (Exception e) {
					return false;
				}
			}
		
		);
	}
}
