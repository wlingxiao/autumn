package autumn.token;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthResponse implements Serializable {
    private final String token;
}
