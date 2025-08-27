package co.com.pragma.api.validators;

import co.com.pragma.api.enums.ErrorCodes;
import co.com.pragma.api.exceptions.JakartaValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = getError(request);
        Map<String, Object> attrs = new HashMap<>();

        ErrorCodes errorCode = Arrays.stream(ErrorCodes.values())
              .filter(ec -> ec.getExceptionClass().equals(error.getClass()))
              .findFirst()
              .orElse(ErrorCodes.INTERNAL_SERVER_ERROR);

        attrs.put("status", errorCode.getStatus().value());
        attrs.put("message", error.getMessage());
        attrs.put("code", errorCode.getCode());

        if(error instanceof JakartaValidationException) {
            attrs.put("errors", ((JakartaValidationException) error).getErrors());
        }

        return attrs;
    }

}