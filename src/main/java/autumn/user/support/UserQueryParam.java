package autumn.user.support;


import lombok.Data;

@Data
class UserQueryParam {

    private String username;

    private Integer page;

    private Integer size;

}
