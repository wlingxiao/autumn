package autumn.user.support;


import autumn.user.User;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserForm {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
