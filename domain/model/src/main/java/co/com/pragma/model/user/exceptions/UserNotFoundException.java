package co.com.pragma.model.user.exceptions;

import co.com.pragma.model.exceptions.DomainException;

public class UserNotFoundException extends DomainException {
	public UserNotFoundException(String email) {
		super("User with email " + email + " not found");
	}
}
