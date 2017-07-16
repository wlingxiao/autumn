package autumn;

import autumn.user.User;
import autumn.user.support.UserRepository;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UserTest extends AbstractIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testListAllUser() throws Exception {
        val user = new User("test", "111111");
        userRepository.save(user);

        val mvcResult = mockMvc.perform(get("/user")).andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), containsString("test"));
    }

}
