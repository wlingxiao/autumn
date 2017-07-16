package autumn.user.web;

import autumn.user.User;
import autumn.user.support.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = {POST})
    public ResponseEntity<?> createUser(@Valid UserForm userForm) {
        val now = now();
        val user = new User(userForm.getUsername(), userForm.getEmail(), userForm.getPassword(), now, now);
        val savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser.getId(), CREATED);
    }

    @RequestMapping(value = "", method = {GET})
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }

    private Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

}
