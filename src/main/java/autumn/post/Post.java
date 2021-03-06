package autumn.post;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "t_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String title;

    @Column(length = 1000)
    private String content;

    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    @Column(name = "last_update_time", nullable = false)
    private Timestamp lastUpdateTime;

    @Column(name = "t_user_id", nullable = false)
    private Long userId;

    public Post() {
    }

    /**
     * only for test
     */
    public Post(Long id) {
        this.id = id;
    }

    /**
     * only for test
     */
    public Post(Long id, String title, String content, Timestamp createTime, Timestamp lastUpdateTime, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.userId = userId;
    }

    public Post(String title, String content, Timestamp createTime, Timestamp lastUpdateTime, Long userId) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.userId = userId;
    }
}
