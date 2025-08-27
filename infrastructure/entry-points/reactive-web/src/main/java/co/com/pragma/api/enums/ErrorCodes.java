package co.com.pragma.api.enums;

import co.com.pragma.api.exceptions.JakartaValidationException;
import co.com.pragma.model.user.exceptions.DuplicatedEmailException;
import co.com.pragma.model.user.exceptions.InvalidSalaryException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodes {
    DUPLICATED_EMAIL("400_DUPLICATED_EMAIL", HttpStatus.BAD_REQUEST, DuplicatedEmailException.class),
    INVALID_SALARY("400_INVALID_SALARY", HttpStatus.BAD_REQUEST, InvalidSalaryException.class),
    INVALID_FIELD("400_INVALID_FIELD", HttpStatus.BAD_REQUEST, JakartaValidationException.class),
    INTERNAL_SERVER_ERROR("500_INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, RuntimeException.class);


    private final String code;
    private final HttpStatus status;
    private final Class<? extends RuntimeException> exceptionClass;

    ErrorCodes(String code, HttpStatus status, Class<? extends RuntimeException> exceptionClass) {
        this.code = code;
        this.status = status;
        this.exceptionClass = exceptionClass;
    }
}