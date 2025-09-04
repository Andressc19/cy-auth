package co.com.pragma.api.config;

import co.com.pragma.model.user.gateways.JwtTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtReactiveFilter implements WebFilter {
	
	private final JwtTokenGenerator jwtTokenGenerator;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			
			return jwtTokenGenerator.validateToken(token)
				.flatMap(isValid -> {
					if (!isValid) {
						return chain.filter(exchange);
					}
					
					return jwtTokenGenerator.getEmailFromToken(token)
						.flatMap(roleId -> jwtTokenGenerator.getEmailFromToken(token)
							.map(email -> {
								List<SimpleGrantedAuthority> authorities =
									List.of(new SimpleGrantedAuthority(roleId));
								return new UsernamePasswordAuthenticationToken(email, null, authorities);
							})
						)
						.flatMap(auth -> chain.filter(exchange)
							.contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)));
				});
		}
		
		return chain.filter(exchange);
	}
}