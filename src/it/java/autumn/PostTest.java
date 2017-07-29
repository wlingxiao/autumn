package autumn;

import autumn.common.DateTimeUtil;
import autumn.post.Post;
import autumn.post.support.PostForm;
import autumn.post.support.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostTest extends AbstractIntegrationTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        val post = new Post("test_title", "test_content",
                DateTimeUtil.now(), 1L);
        post.setId(1L);
        postRepository.saveAndFlush(post);
    }

    @Test
    public void testLoadPostById() throws Exception {
        val mvcResult = mockMvc.perform(get("/posts" + "/1")).andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), containsString("test_title"));
    }

    @Test
    public void testCreatePost() throws Exception {
        val postForm = new PostForm("test_create_post_title", "test_create_post_content");
        mockMvc.perform(post("/posts")
                    .contentType(APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(postForm)))
                .andExpect(status().isCreated());
    }

    @After
    public void tearDown() {
        postRepository.deleteAll();
    }
}
