package autumn.user.support;

import autumn.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Api
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "获取用户列表", notes = "")
    @RequestMapping(value = "", method = {GET})
    public UserResponse<List<ListUserResponse>> listAllUsers() {

        val users = userService.listAllUsers()
                .stream()
                .map(ListUserResponse::new)
                .collect(Collectors.toList());

        return new UserResponse<>("Success", users);
    }

    @ApiOperation(value = "创建用户", notes = "根据 User 对象创建用户")
    @RequestMapping(value = "", method = {POST}, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserForm userForm) {

        val now = now();
        val user = new User(userForm.getUsername(), userForm.getEmail(), userForm.getPassword(), now, now);
        val savedUser = userService.save(user);

        return new ResponseEntity<>(savedUser.getId(), CREATED);
    }

    @ApiOperation(value = "检查用户名是否存在")
    @RequestMapping(value = "/username/{username}", method = HEAD)
    public ResponseEntity<?> checkUsernameExist(@PathVariable("username") String username) {
        val user = userService.findByUsername(username);
        return new ResponseEntity(user != null ? FOUND : NOT_FOUND);
    }

    @ApiOperation(value = "检查邮箱是否存在")
    @RequestMapping(value = "/email/{email:.+}", method = HEAD)
    public ResponseEntity<?> checkEmailExist(@PathVariable("email") String email) {
        val user = userService.findByEmail(email);
        return new ResponseEntity(user != null ? FOUND : NOT_FOUND);
    }

    private Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

}
