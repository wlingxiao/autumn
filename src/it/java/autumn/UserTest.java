package autumn;

import autumn.user.User;
import autumn.user.support.UserRepository;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends AbstractIntegrationTests {

    private Timestamp now;

    @Before
    public void setUp() {
        now = new Timestamp(System.currentTimeMillis());
        val user = new User("test", "test@test.com", "111111", now, now);
        userRepository.save(user);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testListAllUser() throws Exception {
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

    @Test
    public void testCheckUsernameExist() throws Exception {
        mockMvc.perform(get("/users/username/" + "test"))
                .andExpect(status().isFound());
    }

    @Test
    public void testCheckUsernameNotExist() throws Exception {
        mockMvc.perform(get("/users/username/" + "test1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCheckEmailExist() throws Exception {
        mockMvc.perform(get("/users/email/" + "test@test.com"))
                .andExpect(status().isFound());
    }

    @Test
    public void testCheckEmailNotExist() throws Exception {
        mockMvc.perform(get("/users/email/" + "test1@test.com"))
                .andExpect(status().isNotFound());
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

}
