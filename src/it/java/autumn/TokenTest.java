package autumn;

import autumn.user.User;
import autumn.user.support.UserRepository;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TokenTest extends AbstractIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        val user = new User("token_test", "token_test@test.com", "111111", now, now, (short) 1);
        userRepository.save(user);
    }

    @Test
    @Ignore
    public void testGetToken() throws Exception {
        val ret = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", "token_test")
                .param("password", "111111")
        ).andReturn();
        assertThat(ret.getResponse().getCookie("Authentication")).isNotNull();
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

}
