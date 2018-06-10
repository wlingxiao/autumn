package autumn.user.support;

import autumn.user.User;
import autumn.user.UserQueryDTO;
import autumn.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static autumn.common.DateTimeUtil.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@Api(tags = "用户")
@RestController
@RequestMapping(value = "/admin/users", produces = APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "获取用户列表")
    @GetMapping
    public UserResult listUsers(UserQueryParam queryParam) {
        val userQueryDto = new UserQueryDTO();
        userQueryDto.setUsername(queryParam.getUsername());
        val pageable = PageUtils.createDescPage(queryParam.getPage(), queryParam.getSize(), "lastVisitTime");
        val users = userService.findAll(userQueryDto, pageable);
        return new UserResult(users);
    }

    @ApiOperation(value = "创建用户")
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserForm userForm) {
        val now = now();
        val user = new User(userForm.getUsername(), userForm.getEmail(), userForm.getPassword(), now, now, (short) 1);
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

}
