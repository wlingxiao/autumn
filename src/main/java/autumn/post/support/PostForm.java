package autumn.post.support;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostForm {

    @ApiModelProperty(value = "文章标题", required = true)
    @NotNull
    private String title;

    @ApiModelProperty(value = "文章内容", required = true)
    @NotNull
    private String content;

    public PostForm() {
    }

    public PostForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
