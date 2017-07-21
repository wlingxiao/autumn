package autumn.user.support;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserForm {

    @ApiModelProperty(value = "用户名", required = true)
    @NotNull
    private String username;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotNull
    private String email;

    @ApiModelProperty(value = "密码", required = true)
    @NotNull
    private String password;

    @ApiModelProperty(value = "确认密码", required = true)
    @NotNull
    private String confirmPassword;

    public UserForm() {
    }

    public UserForm(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
