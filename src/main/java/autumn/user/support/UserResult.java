package autumn.user.support;


import autumn.Result;
import autumn.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResult extends Result {

    private List<SimpleUser> data;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long total;

    public UserResult(Page<User> users) {
        super(200, null);
        data = users.getContent().stream().map(SimpleUser::new).collect(Collectors.toList());
        total = users.getTotalElements();
    }

    @Data
    static class SimpleUser {

        private Long id;

        private String username;

        private String email;

        private Long signUpTime;

        private Long lastVisitTime;

        public SimpleUser(User user) {
            id = user.getId();
            username = user.getUsername();
            email = user.getEmail();
            signUpTime = user.getSignUpTime().getTime();
            lastVisitTime = user.getLastVisitTime().getTime();
        }

    }
}
