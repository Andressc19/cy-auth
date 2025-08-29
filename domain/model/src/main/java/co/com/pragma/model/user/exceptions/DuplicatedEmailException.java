package co.com.pragma.model.user.exceptions;

import co.com.pragma.model.exceptions.DomainException;

public class DuplicatedEmailException extends DomainException {
    public DuplicatedEmailException(String email) {
      super("User with this email " + email + " already exists");
    }
}
