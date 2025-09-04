package co.com.pragma.model.user.exceptions;

import co.com.pragma.model.exceptions.DomainException;

public class InvalidCredentialsException extends DomainException {
	public InvalidCredentialsException() {
		super("Invalid email or password incorrect");
	}
}
