package co.com.pragma.jwttoken;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.jwt")
public record JwtProperties (
	String secret,
	String issuer,
	String audience,
	String expires
){}