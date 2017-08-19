package autumn.common;

import java.util.Objects;

public final class ObjectsUtil {

    public static <T> boolean equal(T a, T b) {
        return Objects.equals(a, b);
    }

}
