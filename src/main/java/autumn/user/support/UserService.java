package autumn.user.support;

import autumn.user.User;
import autumn.user.UserQueryDTO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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

    public Page<User> findAll(UserQueryDTO userQueryDTO, Pageable pageable) {
        val userPage = userRepository.findAll((root, query, cb) -> {
            val predicates = new ArrayList<Predicate>();
            if (!StringUtils.isEmpty(userQueryDTO.getUsername())) {
                predicates.add(cb.equal(root.get("username"), userQueryDTO.getUsername()));
            }
            val p = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(p));
        }, pageable);
        return userPage;
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
