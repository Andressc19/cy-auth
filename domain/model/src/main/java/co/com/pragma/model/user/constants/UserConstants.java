package co.com.pragma.model.user.constants;

import java.math.BigDecimal;

public final class UserConstants {
    private UserConstants() {}

    public static final BigDecimal MIN_SALARY = BigDecimal.ZERO;
    public static final BigDecimal MAX_SALARY = new BigDecimal("15000000");
}
