package autumn.token;

import autumn.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResult extends Result {

    private String token;

    public TokenResult(int code, String token) {
        this.code = code;
        this.token = token;
    }
}
