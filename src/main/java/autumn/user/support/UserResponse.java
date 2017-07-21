package autumn.user.support;

import lombok.Data;

@Data
public class UserResponse<T> {

    private String message;

    private T data;

    public UserResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
