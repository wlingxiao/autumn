package autumn.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {

    public static Pageable createDescPage(Integer page, Integer size, String column) {
        int p;
        if (page == null || page <= 0) {
            p = 0;
        } else {
            p = page - 1;
        }
        int s;
        if (size == null || size > 50 || size <= 0) {
            s = 10;
        } else {
            s = size;
        }
        return new PageRequest(p, s, new Sort(Sort.Direction.DESC, column));
    }

}
