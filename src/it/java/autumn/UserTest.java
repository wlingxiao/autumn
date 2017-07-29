package autumn;

import autumn.user.User;
import autumn.user.support.UserForm;
import autumn.user.support.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends AbstractIntegrationTests {

    private Timestamp now;

    @Autowired
    private ObjectMapper objectMapper;

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

        val userForm = new UserForm("test_create_user",
                "test_create_user@test.com", "111111", "111111");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(userForm)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCheckUsernameExist() throws Exception {
        mockMvc.perform(head("/users/username/" + "test"))
                .andExpect(status().isFound());
    }

    @Test
    public void testCheckUsernameNotExist() throws Exception {
        mockMvc.perform(head("/users/username/" + "test1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCheckEmailExist() throws Exception {
        mockMvc.perform(head("/users/email/" + "test@test.com"))
                .andExpect(status().isFound());
    }

    @Test
    public void testCheckEmailNotExist() throws Exception {
        mockMvc.perform(head("/users/email/" + "test1@test.com"))
                .andExpect(status().isNotFound());
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

}
