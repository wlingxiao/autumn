package autumn.user.support;

import autumn.user.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(final String username) {
        Assert.notNull(username, "用户名不能为空");

        return userRepository.findByUsername(username);
    }

    public User findByEmail(final String email) {
        Assert.notNull(email, "邮箱不能为空");
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(final Long id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        return user;
    }
}
