package autumn.post.support;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {

    protected long count;

    protected List<T> data;

    public PageResponse() {
    }

    public PageResponse(long count, List<T> data) {
        this.count = count;
        this.data = data;
    }
}
