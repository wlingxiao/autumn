package autumn.token;

import lombok.Data;

import java.io.Serializable;

@Data
class AuthResponse implements Serializable {
    private final String token;
}
