package autumn.user.support;

import autumn.user.User;
import lombok.Data;

@Data
public class ListUserResponse {

    private Long id;

    private String username;

    private String email;

    private Long signUpTime;

    private Long lastVisitTime;

    public ListUserResponse(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        signUpTime = user.getSignUpTime().getTime();
        lastVisitTime = user.getLastVisitTime().getTime();
    }

}
