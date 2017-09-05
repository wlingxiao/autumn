package autumn;

import autumn.user.User;
import autumn.user.support.UserRepository;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TokenTest extends AbstractIntegrationTests {

    private Timestamp now;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        now = new Timestamp(System.currentTimeMillis());
        val user = new User("token_test", "token_test@test.com", "111111", now, now);
        userRepository.save(user);
    }

    @Test
    public void testGetToken() throws Exception {
        val ret = mockMvc.perform(post("/token")
                .param("username", "token_test")
                .param("password", "111111")
        ).andReturn();

    }

}
