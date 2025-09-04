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

@Service
public class JwtTokenGeneratorAdapter implements JwtTokenGenerator {
	
	private final JwtProperties properties;
	private final SecretKey key;
	
	public JwtTokenGeneratorAdapter(JwtProperties properties) {
		this.properties = properties;
		this.key = Keys.hmacShaKeyFor(properties.secret().getBytes(StandardCharsets.UTF_8));
	}
	
	@Override
	public Mono<String> generateAccessToken(String email, String roleName) {
		return Mono.fromCallable(() -> {
			
			Instant now = Instant.now();
			Instant expiry = now.plusSeconds(Long.parseLong(properties.expires()));
			
			return Jwts.builder()
				.subject(email)
				.claim("role", roleName)
				.issuedAt(new Date())
				.expiration(Date.from(expiry))
				.signWith(key)
				.compact();
		});
	}
	
	@Override
	public Mono<String> getEmailFromToken(String token) {
		return Mono.fromCallable(() -> {
				Claims claims = Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload();
				
				return claims.getSubject();
			}
		);
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
