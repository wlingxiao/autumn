package autumn.token;

import lombok.Data;

@Data
public class TokenUser {

    private Long id;

    private String username;

    public TokenUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public TokenUser() {
    }
}
