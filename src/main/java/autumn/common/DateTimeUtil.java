package autumn.common;

import java.sql.Timestamp;

public final class DateTimeUtil {
    private DateTimeUtil() {}

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }
}
