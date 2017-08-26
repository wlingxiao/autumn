package autumn.daily;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "daily_titles")
public class Title {

    @Id
    private String id;

    private String date;

    private List<Story> stories;

    @Data
    public static class Story {
        private List<String> images;

        private Integer type;

        @Field("id")
        private Integer newsId;

        @Field("ga_prefix")
        private String gaPrefix;

        private String title;
    }
}
