package autumn.token;

import autumn.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TokenResult extends Result {

    private String token;

    public TokenResult(int code, String token) {
        this.code = code;
        this.token = token;
    }
}
