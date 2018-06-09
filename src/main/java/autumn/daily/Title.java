package autumn.daily;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "t_title")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pub_data")
    private Integer pubDate;

    private String images;

    private Short type;

    @Column(name = "pub_id")
    private Long pubId;

    @Column(name = "ga_prefix")
    private String gaPrefix;
    
    private String title;

}
