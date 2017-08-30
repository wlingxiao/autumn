package autumn.daily;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "daily_news")
@Data
public class News {

    @Id
    private String id;

    private String body;

    @Field("image_source")
    private String imageSource;

    private String title;

    private String image;

    @Field("share_url")
    private String shareUrl;

    private List<String> js;

    @Field("ga_prefix")
    private String gaPrefix;

    private List<String> images;

    private int type;

    @Field("id")
    private Integer newsId;

    private List<String> css;

    private String summary;
}
