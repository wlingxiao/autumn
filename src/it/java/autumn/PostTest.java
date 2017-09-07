package autumn;

import autumn.post.Post;
import autumn.post.support.PostForm;
import autumn.post.support.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static autumn.common.DateTimeUtil.now;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostTest extends AbstractIntegrationTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long postId;

    @Before
    public void setUp() {
        val post = new Post("test_title", "test_content",
                now(), now(), 1L);
        postId = postRepository.saveAndFlush(post).getId();
    }

    @Test
    public void testLoadPostById() throws Exception {
        mockMvc.perform(get("/posts/" + postId))
                .andExpect(content().string(containsString("test_title")));
    }

    @Test
    public void testLoadPostPage() throws Exception {
        for (int i = 0; i < 10; i++) {
            val post = new Post("test_title" + i, "test_content" + 1,
                    now(), now(), 1L);
            postRepository.saveAndFlush(post);
        }
        mockMvc.perform(get("/posts").param("page", "1"))
                .andExpect(content().string(containsString("test_title5")));
    }

    @Test
    public void testCreatePost() throws Exception {
        val postForm = new PostForm("test_create_post_title", "test_create_post_content");
        mockMvc.perform(post("/posts")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(postForm)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdatePost() throws Exception {
        val postForm = new PostForm("update_post_title", "update_post_content");
        mockMvc.perform(put("/posts/" + postId)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(postForm)))
                .andExpect(status().isCreated());
    }

    @After
    public void tearDown() {
        postRepository.deleteAll();
    }
}
