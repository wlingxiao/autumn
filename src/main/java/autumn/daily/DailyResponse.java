package autumn.daily;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DailyResponse {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "Id", required = true, dataType = "java.lang.String")
    private Long id;

    private String title;

    private String content;

    private String image;

    private String summary;

    public DailyResponse() {
    }

    public DailyResponse(Long id, String title, String content, String image, String summary) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.summary = summary;
    }
}
