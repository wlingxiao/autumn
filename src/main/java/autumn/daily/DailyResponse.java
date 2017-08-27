package autumn.daily;

import lombok.Data;

@Data
public class DailyResponse {

    private String id;

    private String title;

    private String content;

    private String image;

    private String summary;

    public DailyResponse() {
    }

    public DailyResponse(String id, String title, String content, String image, String summary) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.summary = summary;
    }
}
