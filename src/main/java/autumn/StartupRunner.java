package autumn;

import autumn.user.User;
import autumn.user.support.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static autumn.common.DateTimeUtil.now;

@Component
@Profile("dev")
public class StartupRunner implements CommandLineRunner {

    private final static Logger LOG = LoggerFactory.getLogger(StartupRunner.class);

    private UserRepository userRepository;

    @Autowired
    public StartupRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        LOG.debug("insert admin test data");
        User user = new User("admin", "admin@admin.com", "111111", now(), now());
        userRepository.save(user);
    }
}
