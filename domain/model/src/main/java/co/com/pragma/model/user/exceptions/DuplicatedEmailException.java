package co.com.pragma.model.user.exceptions;

public class DuplicatedEmailException extends UserValidationException {
    public DuplicatedEmailException(String email) {
      super("User with this email " + email + " already exists");
    }
}
