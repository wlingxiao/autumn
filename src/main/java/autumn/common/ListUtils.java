package autumn.common;


import java.util.List;

public final class ListUtils {

    private ListUtils() {
    }

    public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
        int len = list.size();
        if (fromIndex > len || toIndex > len) {
            return list;
        }
        return list.subList(fromIndex, toIndex);
    }

}
