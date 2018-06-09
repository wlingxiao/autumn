package autumn.daily;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "t_content")
@Data
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @Column(name = "image_source")
    private String imageSource;

    private String title;

    private String image;

    @Column(name = "share_url")
    private String shareUrl;

    private String js;

    @Column(name = "ga_prefix")
    private String gaPrefix;

    private String images;

    private int type;

    @Column(name = "pub_id")
    private Long pubId;

    private String css;

    private String summary;
}
