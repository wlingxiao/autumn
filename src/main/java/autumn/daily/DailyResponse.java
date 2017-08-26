package autumn.daily;

import lombok.Data;

@Data
public class DailyResponse {

    private String id;

    private String title;

    private String content;

    public DailyResponse() {
    }

    public DailyResponse(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
