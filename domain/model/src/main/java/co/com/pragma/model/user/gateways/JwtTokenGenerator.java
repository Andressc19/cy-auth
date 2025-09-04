package co.com.pragma.model.user.gateways;

public interface JwtTokenGenerator {
	String generateAccessToken(String email);
	String getEmailFromToken(String token);
	Boolean validateToken(String token);
}
