package autumn.common;

import java.util.Objects;

public class ObjectsUtil {

    public static <T> boolean equal(T a, T b) {
        return Objects.equals(a, b);
    }

}
