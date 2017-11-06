/*
package autumn.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;


@Data
@Document(indexName = "autumn", type = "post", shards = 1, replicas = 0, refreshInterval = "-1")
public class PostType {
    private String id;

    private String title;

    private String content;

    private Long createTime;

    private Long lastUpdateTime;

    private Long userId;

    private String highlight;

    public PostType() {
    }

    public PostType(String title, String content, Long createTime, Long lastUpdateTime, Long userId) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.userId = userId;
    }
}
*/
