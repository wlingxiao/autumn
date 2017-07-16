package autumn;

import autumn.user.User;
import autumn.user.support.UserRepository;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends AbstractIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testListAllUser() throws Exception {
        val now = new Timestamp(System.currentTimeMillis());
        val user = new User("test", "test@test.com", "111111", now, now);
        userRepository.save(user);

        val mvcResult = mockMvc.perform(get("/users")).andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), containsString("test@test.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        val params = new LinkedMultiValueMap<String, String>();
        params.add("username", "test_create_user");
        params.add("email", "test_create_user@test.com");
        params.add("password", "111111");

        mockMvc.perform(post("/users")
                .params(params))
                .andExpect(status().isCreated());
    }

}
