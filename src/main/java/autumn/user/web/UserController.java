package autumn.user.web;

import autumn.user.User;
import autumn.user.support.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = {POST})
    public String createUser(@Valid UserForm userForm) {
        return "msg";
    }

    @RequestMapping(value = "", method = {GET})
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }

}
