package co.com.pragma.api.config;

import co.com.pragma.api.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtReactiveFilter jwtReactiveFilter;
	
	private static final String[] WHITE_LIST = {
		ApiConstants.AUTH_PATH,
		"/v3/api-docs/**",
		"/v3/api-docs/swagger-config",
		"/swagger-ui.html",
		"/swagger-ui/**",
		"/webjars/**",
		"/auth/**"
	};
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
			.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
			.exceptionHandling( exceptions -> exceptions
				.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
			.authorizeExchange(exchanges -> exchanges
				.pathMatchers(WHITE_LIST)
				.permitAll()
				.anyExchange()
				.authenticated()
			)
			.addFilterAt(jwtReactiveFilter, SecurityWebFiltersOrder.AUTHENTICATION)
			.build();
	}
}
