package autumn.token;

import lombok.Data;

import java.io.Serializable;

@Data
class AuthRequest implements Serializable {

    private String username;

    private String password;

}
