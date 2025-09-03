package co.com.pragma.model.user.exceptions;

import co.com.pragma.model.exceptions.DomainException;

public class DuplicatedUserException extends DomainException {
    public DuplicatedUserException() {
      super("User with this email or identificationNumber already exists");
    }
}
