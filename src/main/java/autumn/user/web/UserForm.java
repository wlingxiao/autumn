package autumn.user.web;


import autumn.user.User;

import javax.validation.constraints.NotNull;

public class UserForm {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public UserForm() {
    }

    public UserForm(User user) {
        username = user.getUsername();
        password = user.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
