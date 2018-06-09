package autumn.token;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
class AuthResponse implements Serializable {

    @ApiModelProperty(value = "token", required = true)
    private String token;
}
