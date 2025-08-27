package co.com.pragma.api.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class JakartaValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public JakartaValidationException(Map<String, String> errors) {
        super("Validation failed");
        this.errors = errors;
    }
}
